package com.ktb.lukas.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_pwd")
    private String password;
    @Column(name = "user_name")
    private String nickname;
    @Column(name = "profile_image")
    private String image;

    @CreatedDate
    @Column(name = "reg_dat")
    private LocalDateTime createdDate;  //자동으로 생성일자 입력

    @LastModifiedDate
    @Column(name = "upd_dat")
    private LocalDateTime lastModifiedDate;  //자동으로 수정일자 입력

    @OneToMany(mappedBy = "author")
    List<Post> posts = new ArrayList<>();

    public User(String email, String password, String nickname, String image) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.image = image;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
    public void changeImage(String image) {this.image = image; }
}