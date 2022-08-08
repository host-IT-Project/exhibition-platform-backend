package com.hostit.exhibitionplatform.domain.auth.exception;

import com.hostit.exhibitionplatform.global.error.ErrorCdoe;
import com.hostit.exhibitionplatform.global.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class CustomAuthException extends BusinessException {

    private final ErrorCdoe errorCode;
    public CustomAuthException(ErrorCdoe errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public CustomAuthException(ErrorCdoe errorCode, String detailMessage) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
