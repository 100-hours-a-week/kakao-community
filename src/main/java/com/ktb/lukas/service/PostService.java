package com.ktb.lukas.service;

import com.ktb.lukas.dto.PostRequestDto;
import com.ktb.lukas.dto.PostResponseDto;
import com.ktb.lukas.entity.Post;
import com.ktb.lukas.entity.User;
import com.ktb.lukas.event.PostViewedEvent;
import com.ktb.lukas.exception.CustomException;
import com.ktb.lukas.exception.ErrorCode;
import com.ktb.lukas.repository.PostRepository;
import com.ktb.lukas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public PostResponseDto createPost(Long userId, PostRequestDto request) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                request.getImage(),
                author
        );

        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    @Transactional
    public PostResponseDto getPost(Long userId, Long postId) {
        Post post = findPost(postId);
        publisher.publishEvent(
                new PostViewedEvent(userId, postId)  // postView 이벤트 발행함 스프링 이벤트 시스템이 리스너로 보냄
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long userId,
                                      Long postId,
                                      PostRequestDto request) {

        Post post = findPost(postId);

        if (!post.getAuthor().getId().equals(userId)) {
            throw new CustomException(ErrorCode.POST_UPDATE_FORBIDDEN); // 게시글 작성자가 아니면 수정할 수 없음
        }

        post.changeTitle(request.getTitle());
        post.changeContent(request.getContent());

        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {

        Post post = findPost(postId);

        if (!post.getAuthor().getId().equals(userId)) {
            throw new CustomException(ErrorCode.POST_DELETE_FORBIDDEN); // 게시글 작성자가 아니면 삭제할 수 없음
        }

        postRepository.delete(post);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }
}