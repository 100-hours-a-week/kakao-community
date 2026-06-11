package com.ktb.lukas.service;

import com.ktb.lukas.dto.*;
import com.ktb.lukas.entity.User;
import com.ktb.lukas.exception.CustomException;
import com.ktb.lukas.exception.ErrorCode;
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
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        if(userRepository.existsByNickname(request.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
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
        User user = findId(userId);
        return new UserResponseDto(user);
    }
    @Transactional
    public UserResponseDto updateSet(Long userId, UserRequestDto request) {
        User user = findId(userId);
        if (request.getNickname() == null &&
                request.getImage() == null) {
            throw new CustomException(ErrorCode.MISSING_PROFILE_UPDATE_FIELD);
        }
        String nickname = request.getNickname();
        if (nickname != null && !nickname.equals(user.getNickname())) {
            if (nickname.isBlank()) {
                throw new CustomException(ErrorCode.EMPTY_NICKNAME);
            }

            if (userRepository.existsByNickname(nickname)) {
                throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
            }
            user.changeNickname(nickname);
        }
        if (request.getImage() != null) {
            user.changeImage(request.getImage());
        }
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findId(userId);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public void checkEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new CustomException(ErrorCode.EMPTY_EMAIL);
        }
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
    }
    @Transactional(readOnly = true)
    public void checkNickname(String nickname) {

        if (nickname == null || nickname.isBlank()) {
            throw new CustomException(ErrorCode.EMPTY_NICKNAME);
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }
    private User findId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException( ErrorCode.USER_NOT_FOUND));
    }

}