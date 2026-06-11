package com.ktb.lukas.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // message에 errorCode 메세지 옮기고

        this.errorCode = errorCode; // errorCode 필드에 errorCode 저장
    }
}