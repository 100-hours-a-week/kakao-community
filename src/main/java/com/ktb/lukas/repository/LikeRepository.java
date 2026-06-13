package com.ktb.lukas.repository;


import com.ktb.lukas.entity.Postlike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Postlike, Long> {

    Optional<Postlike> findByPostIdAndUserId(Long postId, Long userId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);
}
