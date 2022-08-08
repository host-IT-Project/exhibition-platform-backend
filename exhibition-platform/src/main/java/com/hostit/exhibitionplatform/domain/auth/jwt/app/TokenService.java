package com.hostit.exhibitionplatform.domain.auth.jwt.app;

import com.hostit.exhibitionplatform.domain.auth.entity.RoleType;
import com.hostit.exhibitionplatform.domain.auth.exception.jwt.TokenExpiredException;
import com.hostit.exhibitionplatform.domain.auth.jwt.entity.AuthToken;
import com.hostit.exhibitionplatform.domain.auth.jwt.entity.UserRefreshToken;
import com.hostit.exhibitionplatform.domain.auth.jwt.entity.UserRefreshTokenRepository;
import com.hostit.exhibitionplatform.global.config.properties.AppProperties;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;

    public Map<String, AuthToken> reissueToken(String accessToken, String refreshToken) {

        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        Claims claims = validateAccessToken(authToken);
        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (!authRefreshToken.validate()) {
            throw new IllegalArgumentException("INVALID_REFRESH_TOKEN");
        }

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null) {
            throw new IllegalArgumentException("INVALID_REFRESH_TOKEN");
        }

        // 새 accessToken 발급
        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // 리프래쉬 토큰의 만료시간 체크 후 3일 이하면 재발급
        checkRefreshTokenExpireDate(authRefreshToken, userRefreshToken, now);

        Map<String, AuthToken> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", authRefreshToken);

        return tokens;
    }

    private void checkRefreshTokenExpireDate(AuthToken authRefreshToken, UserRefreshToken userRefreshToken, Date now) {
        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            userRefreshToken.changeRefreshToken(authRefreshToken.getToken());
        }
    }

    private Claims validateAccessToken(AuthToken authToken) {
        // token 검증
        // 검증 로직 리팩토링 필요
        try {
            authToken.validate();
        } catch (TokenExpiredException ex) {
            System.out.println("토큰 만료!!");
        }

        // 만료된 토큰이 맞는지 검증
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            throw new IllegalArgumentException("NOT_EXPIRED_TOKEN");
        }
        return claims;
    }


}
