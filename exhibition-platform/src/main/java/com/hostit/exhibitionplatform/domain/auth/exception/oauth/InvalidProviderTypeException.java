package com.hostit.exhibitionplatform.domain.auth.exception.oauth;

import com.hostit.exhibitionplatform.domain.auth.exception.CustomAuthException;
import com.hostit.exhibitionplatform.global.error.ErrorCdoe;

public class InvalidProviderTypeException extends CustomAuthException {
    private final ErrorCdoe errorCdoe;
    private final String detailMessage;

    public InvalidProviderTypeException(ErrorCdoe errorCode) {
        super(errorCode);
        this.errorCdoe = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public InvalidProviderTypeException(ErrorCdoe errorCode, String detailMessage) {
        super(errorCode);
        this.errorCdoe = errorCode;
        this.detailMessage = detailMessage;
    }
}
