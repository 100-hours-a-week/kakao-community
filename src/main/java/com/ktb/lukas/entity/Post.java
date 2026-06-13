package com.ktb.lukas.entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_content")
    private String content;

    @Column(name = "post_Image")
    private String Image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    private Integer likeCount;

    private Integer viewCount;

    public Post(String title, String content, String Image, User author) {
        this.title = title;
        this.content = content;
        this.Image = Image;
        this.author = author;
        this.viewCount = 0;
        this.likeCount = 0;

    }

    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeContent(String content) {
        this.content = content;
    }

    public void increaseViewCount() { this.viewCount++; }
    public void increaseLikeCount() {this.likeCount++;}

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}