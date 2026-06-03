package com.ktb.lukas.controller;

import jakarta.validation.Valid;
import com.ktb.lukas.dto.PostRequestDto;
import com.ktb.lukas.dto.PostResponseDto;
import com.ktb.lukas.Api.ApiResponse;
import com.ktb.lukas.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(
            Authentication authentication,   // 로그인 사용자 정보
            @Valid @RequestBody PostRequestDto request
    ) {
        Long userId = (Long) authentication.getPrincipal(); // jwt 인증된 user_id 꺼냄

        PostResponseDto result = postService.createPost(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of("POST_CREATED", result));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPost(@PathVariable Long postId) {
        PostResponseDto result = postService.getPost(postId);
        return ResponseEntity.ok(
                ApiResponse.of("POST_RETRIEVED", result));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDto request
    ) {
        PostResponseDto result = postService.updatePost(postId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("POST_UPDATED", result));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("POST_DELETED", null));
    }
}