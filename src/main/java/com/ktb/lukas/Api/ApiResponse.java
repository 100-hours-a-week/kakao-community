package com.ktb.lukas.api;

import com.ktb.lukas.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 서버가 클라이언트에게 반환하는 응답형식
@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    public static ApiResponse<Void> error(ErrorCode errorCode) {
        return new ApiResponse<>(
                errorCode.name(),
                errorCode.getMessage(),
                null
        );
    }
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(
                "SUCCESS",
                message,
                data
        );
    }
}