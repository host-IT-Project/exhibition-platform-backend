package com.hostit.exhibitionplatform.domain.auth.jwt.filter;

import com.hostit.exhibitionplatform.domain.auth.exception.AuthErrorCode;
import com.hostit.exhibitionplatform.domain.auth.exception.jwt.TokenValidFailedException;
import com.hostit.exhibitionplatform.domain.auth.jwt.AuthToken;
import com.hostit.exhibitionplatform.domain.auth.jwt.AuthTokenProvider;
import com.hostit.exhibitionplatform.global.util.HeaderUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 사용자 인증 시 Token 을 검증하는 Servlet Filter
 */
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
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", AuthErrorCode.EXPIRED_TOKEN.getCode());
        } catch (JwtException e) {
            request.setAttribute("exception", AuthErrorCode.INVALID_TOKEN.getCode());
        } catch (TokenValidFailedException e) {
            request.setAttribute("exception", AuthErrorCode.AUTHENTICATION_CLIENT_EXCEPTION.getCode());
        }

        filterChain.doFilter(request, response);
    }
}
