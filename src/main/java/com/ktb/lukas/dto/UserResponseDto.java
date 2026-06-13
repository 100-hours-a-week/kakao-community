package com.ktb.lukas.dto;

import lombok.Getter;
import com.ktb.lukas.entity.User;

import java.time.LocalDateTime;


@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.image =  user.getImage();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}

