package com.hostit.exhibitionplatform.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 모든 예외를 잡을 수 있는 Handler
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handlerException(Exception e) {
        log.error("handlerException : {}", e);
        e.printStackTrace();
        final ErrorResponse errorResponse = ErrorResponse.of(GlobalErrorCode.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus()));
    }
}
