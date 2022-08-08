package com.hostit.exhibitionplatform.global.common;

import lombok.Getter;

@Getter
public class SingleResult<T> extends CommonResult {

    private T data;

    public SingleResult(Boolean isSuccess, Integer status, String message, T data) {
        super(isSuccess, status, message);
        this.data = data;
    }
}
