package com.ktb.lukas.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Postlike extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_Id", nullable = false)
    private Post post;



}
