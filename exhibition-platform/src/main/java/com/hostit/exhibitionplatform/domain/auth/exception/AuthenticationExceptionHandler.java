package com.hostit.exhibitionplatform.domain.auth.exception;

import com.hostit.exhibitionplatform.domain.auth.exception.jwt.TokenValidFailedException;
import com.hostit.exhibitionplatform.domain.auth.exception.oauth.InvalidLoginRedirectUriException;
import com.hostit.exhibitionplatform.domain.auth.exception.oauth.InvalidProviderTypeException;
import com.hostit.exhibitionplatform.domain.auth.exception.oauth.OAuthProviderMissMatchException;
import com.hostit.exhibitionplatform.global.error.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice()
public class AuthenticationExceptionHandler {

    /**
     * JWT 만료 예외 핸들링
     */
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(AuthErrorCode.EXPIRED_TOKEN);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(AuthErrorCode.EXPIRED_TOKEN.getStatus())
        );
    }

    /**
     * 유효하지 않은 JWT 예외 핸들링
     */
    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<ErrorResponse> handleJwtException(JwtException e) {
        final ErrorResponse errorResponse;

        if (e.getMessage().equals(AuthErrorCode.NOT_EXPIRED_TOKEN.getMessage())) {
            errorResponse = ErrorResponse.of(AuthErrorCode.NOT_EXPIRED_TOKEN);
        } else {
            errorResponse = ErrorResponse.of(AuthErrorCode.INVALID_TOKEN);
        }

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(AuthErrorCode.INVALID_TOKEN.getStatus())
        );
    }

    @ExceptionHandler(TokenValidFailedException.class)
    protected ResponseEntity<ErrorResponse> handleTokenValidFailedException(TokenValidFailedException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(AuthErrorCode.AUTHENTICATION_CLIENT_EXCEPTION);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(AuthErrorCode.AUTHENTICATION_CLIENT_EXCEPTION.getStatus())
        );
    }

    @ExceptionHandler(OAuthProviderMissMatchException.class)
    protected ResponseEntity<ErrorResponse> handleOAuthProviderMissMatchException(OAuthProviderMissMatchException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(AuthErrorCode.MISS_MATCH_PROVIDER);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(AuthErrorCode.MISS_MATCH_PROVIDER.getStatus())
        );
    }

    @ExceptionHandler(InvalidProviderTypeException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidProviderTypeException(InvalidProviderTypeException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(AuthErrorCode.INVALID_PROVIDER_TYPE);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(AuthErrorCode.INVALID_PROVIDER_TYPE.getStatus())
        );
    }

    @ExceptionHandler(InvalidLoginRedirectUriException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidLoginRedirectUriException(InvalidLoginRedirectUriException e) {
        final ErrorResponse errorResponse = ErrorResponse.of(AuthErrorCode.INVALID_LOGIN_REDIRECT_URI);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(AuthErrorCode.INVALID_LOGIN_REDIRECT_URI.getStatus())
        );
    }

}
