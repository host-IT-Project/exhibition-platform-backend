package com.hostit.exhibitionplatform.domain.auth.oauth.app;

import com.hostit.exhibitionplatform.domain.auth.entity.UserPrincipal;
import com.hostit.exhibitionplatform.domain.auth.oauth.info.OAuth2UserInfoFactory;
import com.hostit.exhibitionplatform.domain.auth.entity.ProviderType;
import com.hostit.exhibitionplatform.domain.auth.entity.RoleType;
import com.hostit.exhibitionplatform.domain.auth.exception.oauth.OAuthProviderMissMatchException;
import com.hostit.exhibitionplatform.domain.auth.oauth.info.OAuth2UserInfo;
import com.hostit.exhibitionplatform.domain.user.entity.User;
import com.hostit.exhibitionplatform.domain.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.hostit.exhibitionplatform.domain.auth.exception.AuthErrorCode.MISS_MATCH_PROVIDER;

/**
 소셜 로그인 성공 시 후속 조치 담당
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        // OAuth Login 을 통해 프로바이더 별 User 의 정보를 받아서 저장
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        User savedUser = userRepository.findByUserId(userInfo.getId());

        if (savedUser != null) {
            if (providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        MISS_MATCH_PROVIDER,
                        "이미 가입된 계정입니다!");
            }
            updateUser(savedUser, userInfo);
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        User user = User.builder()
                .userId(userInfo.getId())
                .username(userInfo.getName())
                .email(userInfo.getEmail())
                .profileImageUrl(userInfo.getImageUrl())
                .providerType(providerType)
                .roleType(RoleType.USER)
                .build();

        return userRepository.saveAndFlush(user);
    }

    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName())) {
            user.changeUsername(userInfo.getName());
        }

        if (userInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(userInfo.getImageUrl())) {
            user.changeProfileImageUrl(userInfo.getImageUrl());
        }

        return user;
    }
}
