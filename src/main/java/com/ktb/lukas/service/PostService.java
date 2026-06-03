package com.ktb.lukas.service;

import com.ktb.lukas.dto.PostRequestDto;
import com.ktb.lukas.dto.PostResponseDto;
import com.ktb.lukas.entity.Post;
import com.ktb.lukas.entity.User;
import com.ktb.lukas.exception.NotFoundException;
import com.ktb.lukas.repository.UserRepository;
import com.ktb.lukas.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto createPost(Long userId, PostRequestDto request) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                request.getImage(),
                author
        );

        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        post.changeTitle(request.getTitle());
        post.changeContent(request.getContent());

        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}