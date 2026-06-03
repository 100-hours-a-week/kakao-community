package com.ktb.lukas.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// 런타임 에러 상속 객체, 런타임에러는 프로그램 자체의 오류가 있을 때 발생하도록 사용한다.
@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final HttpStatus status;

    public BusinessException(String code, HttpStatus status) {
        super(code);
        this.code = code;
        this.status = status;
    }
}
