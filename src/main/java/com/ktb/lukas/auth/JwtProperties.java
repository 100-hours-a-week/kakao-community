package com.ktb.lukas.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")  // 이게 yaml 파일에서 "jwt"라고 써져있는 부분 가져옴
// 코드에서 jwt 설정값을 직접 참조해 쓸 수 있도록 하는 클래스
public class JwtProperties {
    private String secret;
    private long accessTokenExpSeconds;
    private long refreshTokenExpSeconds;
}