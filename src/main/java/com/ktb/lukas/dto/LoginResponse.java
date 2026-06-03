package com.ktb.lukas.dto;

import com.ktb.lukas.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private UserResponseDto user;
    private TokenInfo token;

    public static LoginResponse of(
            User user,
            String accessToken,
            long expiresIn
    ) {
        return new LoginResponse(
                new UserResponseDto(user),
                new TokenInfo(accessToken, expiresIn)
        );
    }
}