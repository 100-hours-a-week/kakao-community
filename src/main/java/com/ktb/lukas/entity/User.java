package com.ktb.lukas.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 40)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @Column(name = "NICKNAME", nullable = false, unique = true, length = 10)
    private String nickname;

    @Column(name = "IMAGE_ID", nullable = true, length = 200)
    private String image;


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