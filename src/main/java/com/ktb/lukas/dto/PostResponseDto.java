package com.ktb.lukas.dto;
import com.ktb.lukas.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private Long authorId;
    private Long viewCount;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.image = post.getImage();
        this.authorId = post.getAuthor().getId();
        this.viewCount = Long.valueOf(post.getViewCount());
    }
}
