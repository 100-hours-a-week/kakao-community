package com.ktb.lukas.config;

import com.ktb.lukas.repository.UserRepository;
import com.ktb.lukas.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Configuration
@Profile("development")
@RequiredArgsConstructor
public class SeedConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner seedRunner() {
        return arguments -> seed(); // 부트 기동 후 1회 실행
    }

    @Transactional
    public void seed() {
        if (userRepository.count() >= 10) return;

        IntStream.rangeClosed(1, 10).forEach(i -> {
            String password = "12341234aS!" + i;

            String encodedPassword = passwordEncoder.encode(password);
            User user = new User("tester" + i + "@adapterz.kr", encodedPassword,
                    "tester" + i, "");

            userRepository.save(user); // 루프 안에서 즉시 DB에 저장
        });
    }
}