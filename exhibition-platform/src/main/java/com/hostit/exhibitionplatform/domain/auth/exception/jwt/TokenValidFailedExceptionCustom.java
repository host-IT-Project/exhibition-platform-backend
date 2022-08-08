package com.hostit.exhibitionplatform.domain.auth.exception.jwt;

import com.hostit.exhibitionplatform.domain.auth.exception.CustomAuthException;
import com.hostit.exhibitionplatform.global.error.ErrorCdoe;
import lombok.Getter;

@Getter
public class TokenValidFailedExceptionCustom extends CustomAuthException {

    private final ErrorCdoe errorCdoe;
    private final String detailMessage;

    public TokenValidFailedExceptionCustom(ErrorCdoe errorCode) {
        super(errorCode);
        this.errorCdoe = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public TokenValidFailedExceptionCustom(ErrorCdoe errorCdoe, String detailMessage) {
        super(errorCdoe);
        this.errorCdoe = errorCdoe;
        this.detailMessage = errorCdoe.getMessage();
    }
}
