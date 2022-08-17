package com.hostit.exhibitionplatform.domain.auth.oauth.info;

import com.hostit.exhibitionplatform.domain.auth.entity.ProviderType;
import com.hostit.exhibitionplatform.domain.auth.exception.oauth.InvalidProviderTypeException;
import com.hostit.exhibitionplatform.domain.auth.oauth.info.impl.FacebookOAuth2UserInfo;
import com.hostit.exhibitionplatform.domain.auth.oauth.info.impl.GoogleOAuth2UserInfo;
import com.hostit.exhibitionplatform.domain.auth.oauth.info.impl.KakaoOAuth2UserInfo;
import com.hostit.exhibitionplatform.domain.auth.oauth.info.impl.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            case FACEBOOK: return new FacebookOAuth2UserInfo(attributes);
            case NAVER: return new NaverOAuth2UserInfo(attributes);
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new InvalidProviderTypeException();
        }
    }
}
