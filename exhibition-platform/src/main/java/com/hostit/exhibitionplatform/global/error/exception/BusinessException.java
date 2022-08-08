package com.hostit.exhibitionplatform.global.error.exception;

import com.hostit.exhibitionplatform.global.error.ErrorCdoe;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCdoe errorCode;

    public BusinessException(ErrorCdoe errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
