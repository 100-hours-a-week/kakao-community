package com.ktb.lukas.dto;
import com.ktb.lukas.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String Image;
    private Long authorId;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.Image = post.getImage();
        this.authorId = post.getAuthor().getId();
    }
}
