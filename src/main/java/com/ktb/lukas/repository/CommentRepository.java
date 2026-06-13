package com.ktb.lukas.repository;

import com.ktb.lukas.entity.Comment;
import com.ktb.lukas.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
