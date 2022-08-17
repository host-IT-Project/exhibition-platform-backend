package com.hostit.exhibitionplatform.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResult {

    private Boolean isSuccess;

    private Integer status;

    private String message;
}
