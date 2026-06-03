package com.ktb.lukas.dto;

import com.ktb.lukas.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @Email                              // 이메일 형식 검증 어노테이션
    @NotBlank                           // 공백 여부 어노테이션
    private String email;

    @NotBlank
    @Size(min = 8)                      // 최소 8자리는 있어야 한다는 조건 어노테이션 (max도 가능)
    private String password;

    @NotBlank                           // 공백 여부 어노테이션
    private String nickname;

    private String image;
}
