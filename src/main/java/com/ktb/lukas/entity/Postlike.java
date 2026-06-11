/*package com.ktb.lukas.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "post_like")
public class Postlike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "post_Id", nullable = false)
    private Long postId;
}*/
