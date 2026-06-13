package com.ktb.lukas.controller;

import com.ktb.lukas.Api.ApiResponse;
import com.ktb.lukas.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping
    public ApiResponse<Void> insertLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    )
    {
        postLikeService.insertLike(postId, userId);
        return ApiResponse.success("좋아요를 눌렀습니다.", null);
    }

    @DeleteMapping
    public ApiResponse<Void> deleteLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    )
    {
        postLikeService.deleteLike(postId, userId);
        return ApiResponse.success("좋아요를 취소했습니다.", null);
    }

}
