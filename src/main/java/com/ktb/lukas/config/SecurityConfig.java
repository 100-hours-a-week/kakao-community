package com.ktb.lukas.config;

import com.ktb.lukas.auth.JwtAuthenticationFilter;
import com.ktb.lukas.Api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tools.jackson.databind.ObjectMapper;

import java.io.PrintWriter;

@Configuration                  // 설정 클래스 선언
@EnableWebSecurity              // Spring Security annotation
@RequiredArgsConstructor        // 생성자 자동 생성
public class SecurityConfig {

    private final ObjectMapper objectMapper;                    // JSON 데이터 변환 객체
    private final JwtAuthenticationFilter jwtAuthenticationFilter;  // JWT 인증 필터 객체

    // 인증할 필요 없이 접근 가능한 API 주소
    private static final String[] PUBLIC_ENDPOINTS = {
            "/users",
            "/auth",
            "/users/token/refresh"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http    // CorsConfigurationSource bean 설정을 따르겠다는말
                // 이걸 설정하지 않으면 백엔드와 프론트엔드 서버가 ip가 다를시 통신할 수 없음
                .cors(Customizer.withDefaults())
                // csrf 공격 보호 비활성화 세션 방식에서는 필요하나 jwt는 stateless 방식이기 때문에 필요없음
                .csrf(AbstractHttpConfigurer::disable)
                // HTTP 요청 인가 설정
                .authorizeHttpRequests(auth -> auth
                        // 회원가입은 누구나 할 수 있게 설정 POST /users
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        // 위에서 설정한 auth랑 refresh도 가능
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        // 나머지는 인증을 해야함
                        .anyRequest().authenticated()
                )
                // 예외 처리 부분
                .exceptionHandling(exception -> exception
                        // 인증되지 않은 사용자 = 미로그인이 자원에 접근할시 처리
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding("UTF-8");

                            // API 규격으로 에러 반환하기 위해 선언
                            ApiResponse<Void> apiResponse = ApiResponse.of(
                                    "UNAUTHORIZED",
                                    null
                            );

                            PrintWriter writer = response.getWriter();
                            writer.write(objectMapper.writeValueAsString(apiResponse));
                            writer.flush();
                        })
                        // 인증은 있지만 권한이 없는 사용자 접근 처리
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding("UTF-8");

                            ApiResponse<Void> apiResponse = ApiResponse.of(
                                    "FORBIDDEN",
                                    null
                            );

                            PrintWriter writer = response.getWriter();
                            writer.write(objectMapper.writeValueAsString(apiResponse));
                            writer.flush();
                        })
                )
                // 스프링 시큐리티 로그인 필터 사용전 커스텀 필터 먼저 실행할 수 있도록 함
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    @Bean   // Brypt 해시 함수를 활용하여 비밀번호 암호화함
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}