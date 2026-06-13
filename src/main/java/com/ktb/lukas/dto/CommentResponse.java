package com.ktb.lukas.dto;

import com.ktb.lukas.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private String comment;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponse(Comment comment) {
        this.comment = comment.getComment();
        this.nickname = comment.getUser().getNickname();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
