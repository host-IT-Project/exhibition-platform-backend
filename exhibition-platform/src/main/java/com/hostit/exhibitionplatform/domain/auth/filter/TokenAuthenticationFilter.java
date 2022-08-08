package com.hostit.exhibitionplatform.domain.auth.filter;

import com.hostit.exhibitionplatform.domain.auth.exception.AuthErrorCode;
import com.hostit.exhibitionplatform.domain.auth.exception.jwt.TokenExpiredException;
import com.hostit.exhibitionplatform.domain.auth.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.hostit.exhibitionplatform.domain.auth.jwt.app.AuthTokenProvider;
import com.hostit.exhibitionplatform.domain.auth.jwt.entity.AuthToken;
import com.hostit.exhibitionplatform.global.util.CookieUtil;
import com.hostit.exhibitionplatform.global.util.HeaderUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hostit.exhibitionplatform.domain.auth.exception.AuthErrorCode.*;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);
        String refreshToken = CookieUtil.getCookie(request, OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        System.out.println("refresh token Test! : " + refreshToken);

        // token 을 재발급 하는 경우
        String path = request.getRequestURI();
        if ("/auth/refresh".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (token.validate()) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (TokenExpiredException ex) {
            request.setAttribute("exception", ex.getErrorCode().getCode());
        }

        filterChain.doFilter(request, response);
    }
}
