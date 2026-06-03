package com.ktb.lukas.service;

import com.ktb.lukas.dto.*;
import com.ktb.lukas.entity.User;
import com.ktb.lukas.exception.NotFoundException;
import com.ktb.lukas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto createUser(UserRequestDto request) {
        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                request.getImage()
        );
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));
        return new UserResponseDto(user);
    }
    @Transactional
    public UserResponseDto updateSet(Long userId, UserRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));
        user.changeNickname(request.getNickname());
        user.changeImage(request.getImage());
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}