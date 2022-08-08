package com.hostit.exhibitionplatform.global.common;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    // 단일건 성공 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(Integer status, String message, T data) {
        return new SingleResult<>(true, status, message, data);
    }

    // 다중건 성공 결과를 처리하느 메소드
    public <T> ListResult<T> getListResult(Integer status, String message, List<T> data) {
        return new ListResult<>(true, status, message, data);
    }

    // 성공 결과만 처리하는 메소드
    public CommonResult getSuccessResult(Integer status, String message) {
        return new CommonResult(true, status, message);
    }
}
