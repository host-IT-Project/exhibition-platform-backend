package com.hostit.exhibitionplatform.domain.auth.exception;

import com.hostit.exhibitionplatform.global.error.ErrorCdoe;
import com.hostit.exhibitionplatform.global.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.hostit.exhibitionplatform.domain.auth.exception.AuthErrorCode.*;


@Slf4j
@RestControllerAdvice(basePackages = "com.hostit.exhibitionplatform.domain.auth")
public class AuthExceptionHandler {

    /**
     * Custom AuthException 처리
     */
    @ExceptionHandler(value = CustomAuthException.class)
    public ResponseEntity<ErrorResponse> handlerCustomAuthException(CustomAuthException e) {
        log.error("errorCode : {}, url : {}, message : {}",
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage());

        final ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(e.getErrorCode().getStatus())
        );
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handlerIllegalArgumentException(IllegalArgumentException ex) {
        ErrorCdoe errorCode = null;
        log.error("error message : {}", ex.getMessage());

        if (ex.getMessage() == INVALID_REFRESH_TOKEN.name()) {
            errorCode = INVALID_REFRESH_TOKEN;
        }

        final ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorCode.getStatus())
        );
    }

    /**
     * InternalAuthenticationServiceException 처리
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> handlerInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        log.error("auth error message : {}\nerror cause : {}", e.getMessage(), e.getCause());

        final ErrorResponse errorResponse = ErrorResponse.of(AUTHENTICATION_EXCEPTION);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(AUTHENTICATION_SERVICE_EXCEPTION.getStatus())
        );
    }

    /**
     * AuthenticationException 처리
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handlerAuthenticationException(AuthenticationException e) {
        log.error("auth error message : {}", e.getMessage());

        final ErrorResponse errorResponse = ErrorResponse.of(AUTHENTICATION_EXCEPTION);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(AUTHENTICATION_EXCEPTION.getStatus())
        );
    }
}
