package com.ktb.lukas.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
// 하나의 HTTP 요청에 한번만 실행되도록 하는 상속 클래스
public class JwtAuthenticationFilter extends OncePerRequestFilter {   // 한번만 검증하기 위해 상속
    // 이거 왜 가져옴? 얘가 토큰 정보 다 가지고 있잖아 가져와서 써야지

    private final JwtProvider jwtProvider;

    //이 주소로 접근하는 사람은 일단 통과 시켜 주기 위함
    private static final String[] WHITE_LIST = {
            "/users",
            "/auth",
            "/users/token/refresh"
    };


    // 화이트 리스트 확인해서 통과시켜주는 실제 로직
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            return PatternMatchUtils.simpleMatch(WHITE_LIST, request.getRequestURI());
        }
        return false;
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 토큰이 없거나 형식이 틀리면 401
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // 토큰 서명 + 만료 검증
            jwtProvider.parse(token);

            // access 토큰인지 확인
            if (!jwtProvider.isAccessToken(token)) {
                throw new IllegalArgumentException("Not access token");
            }
            Long userId = jwtProvider.getUserId(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of()
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            // 여기서는 인증 정보 전달 없이 통과만 시킴
            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}