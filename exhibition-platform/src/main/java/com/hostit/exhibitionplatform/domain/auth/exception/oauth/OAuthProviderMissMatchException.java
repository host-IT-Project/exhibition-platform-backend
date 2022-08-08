package com.hostit.exhibitionplatform.domain.auth.exception.oauth;

import com.hostit.exhibitionplatform.domain.auth.exception.CustomAuthException;
import com.hostit.exhibitionplatform.global.error.ErrorCdoe;
import lombok.Getter;

@Getter
public class OAuthProviderMissMatchException extends CustomAuthException {
    private final ErrorCdoe errorCdoe;
    private final String detailMessage;

    public OAuthProviderMissMatchException(ErrorCdoe errorCode) {
        super(errorCode);
        this.errorCdoe = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    public OAuthProviderMissMatchException(ErrorCdoe errorCode, String detailMessage) {
        super(errorCode);
        this.errorCdoe = errorCode;
        this.detailMessage = detailMessage;
    }
}
