package com.ktb.lukas.controller;

import jakarta.validation.Valid;
import com.ktb.lukas.dto.*;
import com.ktb.lukas.Api.ApiResponse;
import com.ktb.lukas.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(
            @Valid @RequestBody UserRequestDto request
    ) {
        UserResponseDto result = userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/users" + result.getId())
                .body(ApiResponse.of("USER_CREATED", result));
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateSet(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequestDto request
    ) {
        return userService.updateSet(userId, request);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.of("USER_DELETED", null));
    }
}