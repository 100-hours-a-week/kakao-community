package com.ktb.lukas.controller;

import jakarta.validation.Valid;
import com.ktb.lukas.dto.PostRequestDto;
import com.ktb.lukas.dto.PostResponseDto;
import com.ktb.lukas.Api.ApiResponse;
import com.ktb.lukas.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostResponseDto> createPost(
            Authentication authentication,   // 로그인 사용자 정보
            @Valid @RequestBody PostRequestDto request
    ) {
        Long userId = (Long) authentication.getPrincipal(); // jwt 인증된 user_id 꺼냄

        PostResponseDto result = postService.createPost(userId, request);
        return ApiResponse.success(
                "게시글 등록 성공",
                result
        );
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto> getPost(
            Authentication authentication,
            @PathVariable Long postId
    ) {

        Long userId = (Long) authentication.getPrincipal();

        PostResponseDto result = postService.getPost(userId, postId);

        return ApiResponse.success(
                "게시글 가져오기 성공",
                result
        );
    }


    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDto> updatePost(
            Authentication authentication,
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDto request
    ) {
        Long userId = (Long) authentication.getPrincipal();
        PostResponseDto result = postService.updatePost(userId, postId, request);
        return ApiResponse.success(
                "게시글 업데이트 성공",
                result
        );
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(
            Authentication authentication,
            @PathVariable Long postId
    ) {
        Long userId = (Long) authentication.getPrincipal();
        postService.deletePost(userId ,postId);
        return ApiResponse.success(
                "게시글 삭제 성공",
                null
        );
    }
}