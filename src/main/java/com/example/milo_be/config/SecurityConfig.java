package com.example.milo_be.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // 비밀번호 암호화 Bean 등록 (BCrypt 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security 필터 체인 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())  // CORS 설정 활성화
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화 (API 서버용)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());  // 모든 요청 허용 (JWT 필터에서 인증 처리 예정)

        return http.build();
    }

    // CORS 정책 정의
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 🔒 HTTPS 배포 도메인 추가
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://192.168.219.55:3000",
                "http://192.168.219.184:3000",
                "http://211.188.59.173:3000",
                "https://soswithmilo.site",
                "https://www.soswithmilo.site"
        ));

        config.setAllowCredentials(true); // 쿠키/인증정보 허용
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setExposedHeaders(Arrays.asList("Authorization")); // 응답에 포함된 헤더 노출 (필요 시)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
