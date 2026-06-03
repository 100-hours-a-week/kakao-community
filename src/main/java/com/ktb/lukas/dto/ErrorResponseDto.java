package com.ktb.lukas.dto;
import lombok.Getter;

@Getter
public class ErrorResponseDto {

    private final String code;
    private final Object data;

    private ErrorResponseDto(String code) {
        this.code = code;
        this.data = null;
    }

    // 이거 이해하는데 참 오래도 걸렸다..
    // of는 나만에 에러 창구 객체를 만든 느낌이라고 생각하면 편함
    // 내가 나중에 에러가 난 시간을 기록해두고 싶은데? 하면 원래는 생성자도 고치고 해야하는데...
    // of로 선언해두면 of로 선언한 객체만 고치고 사용하는 코드쪽은 건들지 않아도 통일된다고 생각하면 편하다.

    public static ErrorResponseDto of(String code) {
        return new ErrorResponseDto(code);
    }
}
