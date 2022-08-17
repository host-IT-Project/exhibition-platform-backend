package com.hostit.exhibitionplatform.global.error;

import com.hostit.exhibitionplatform.global.util.GsonUtil;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private Boolean isSuccess;
    private int status;
    private String code;
    private String message;
    private List<FieldError> errors;
    private LocalDateTime timestamp;

    private ErrorResponse(String message, int status, String code) {
        this.isSuccess = false;
        this.message = message;
        this.status = status;
        this.errors = new ArrayList<>();
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorResponse of(ErrorCdoe errorCode) {
        return new ErrorResponse(errorCode.getMessage(), errorCode.getStatus(), errorCode.getCode());
    }

    @Getter
    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }

    public String convertJson() {
        return new GsonUtil().toJson(this);
    }
}
