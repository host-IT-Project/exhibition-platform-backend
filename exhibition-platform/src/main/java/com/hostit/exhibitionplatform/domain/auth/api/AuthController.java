package com.hostit.exhibitionplatform.domain.auth.api;

import com.hostit.exhibitionplatform.domain.auth.jwt.app.TokenService;
import com.hostit.exhibitionplatform.domain.auth.jwt.entity.AuthToken;
import com.hostit.exhibitionplatform.global.common.ResponseService;
import com.hostit.exhibitionplatform.global.common.SingleResult;
import com.hostit.exhibitionplatform.global.config.properties.AppProperties;
import com.hostit.exhibitionplatform.global.util.CookieUtil;
import com.hostit.exhibitionplatform.global.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AppProperties appProperties;
    private final TokenService tokenService;
    private final ResponseService responseService;

    private final static String REFRESH_TOKEN = "refresh_token";

    @GetMapping("/refresh")
    public SingleResult<Map<String, String>> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        // access token 확인
        String accessToken = HeaderUtil.getAccessToken(request);
        // refresh token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));

        Map<String, AuthToken> tokens = tokenService.reissueToken(accessToken, refreshToken);

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        // refresh token cookie 재설정
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, tokens.get("refreshToken").getToken(), cookieMaxAge);

        Map<String, String> responseResult = new HashMap<>();
        responseResult.put("newToken", tokens.get("accessToken").getToken());

        return responseService.getSingleResult(
                HttpStatus.OK.value(),
                "성공적으로 토큰이 발급되었습니다!",
                responseResult);
    }
}
