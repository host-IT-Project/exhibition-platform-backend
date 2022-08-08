package com.hostit.exhibitionplatform.domain.auth.exception.jwt;

import com.hostit.exhibitionplatform.domain.auth.exception.CustomAuthException;
import com.hostit.exhibitionplatform.global.error.ErrorCdoe;

public class TokenExpiredException extends CustomAuthException {
    private final ErrorCdoe errorCdoe;
    private final String detailMessage;

    public TokenExpiredException(ErrorCdoe errorCode) {
        super(errorCode);
        this.errorCdoe = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public TokenExpiredException(ErrorCdoe errorCode, String detailMessage) {
        super(errorCode);
        this.errorCdoe = errorCode;
        this.detailMessage = detailMessage;
    }
}
