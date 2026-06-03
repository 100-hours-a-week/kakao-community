package com.ktb.lukas.entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Post {

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

    public Post(String title, String content, String Image, User author) {
        this.title = title;
        this.content = content;
        this.Image = Image;
        this.author = author;

    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}