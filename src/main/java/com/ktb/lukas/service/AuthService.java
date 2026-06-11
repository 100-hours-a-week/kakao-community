package com.ktb.lukas.service;

import com.ktb.lukas.auth.JwtProvider;
import com.ktb.lukas.dto.*;
import com.ktb.lukas.entity.RefreshToken;
import com.ktb.lukas.entity.User;
import com.ktb.lukas.exception.CustomException;
import com.ktb.lukas.exception.ErrorCode;
import com.ktb.lukas.repository.RefreshTokenRepository;
import com.ktb.lukas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;



    public LoginResult login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }


        String accessToken = jwtProvider.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );

        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(
                new RefreshToken(
                        refreshToken,
                        user.getId(),
                        LocalDateTime.now().plusDays(14)
                )
        );

        return new LoginResult(
                LoginResponse.of(user, accessToken, jwtProvider.getAccessTokenValidityInMilliseconds()),
                refreshToken
        );
    }

    // 액세스 토큰 재발급
    public TokenResult refreshAccessToken(String refreshToken) {

        RefreshToken saved = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        if (saved.isExpired()) {
            refreshTokenRepository.delete(saved);
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        User user = userRepository.findById(saved.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        String newAccessToken = jwtProvider.createAccessToken(
                user.getId(),
                user.getEmail(),
                user.getNickname()
        );

        // Refresh Token 회전 (Rotation)
        String newRefreshToken = jwtProvider.createRefreshToken(user.getId());
        refreshTokenRepository.delete(saved);
        refreshTokenRepository.save(
                new RefreshToken(
                        newRefreshToken,
                        user.getId(),
                        LocalDateTime.now().plusDays(14)
                )
        );

        return new TokenResult(
                new TokenInfo(newAccessToken, 3600),
                newRefreshToken
        );
    }

}
