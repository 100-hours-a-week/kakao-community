package com.ktb.lukas.entity;

import com.ktb.lukas.entity.BaseTime;
import jakarta.persistence.Column;

public class Comment extends BaseTime{

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int postId;

}
