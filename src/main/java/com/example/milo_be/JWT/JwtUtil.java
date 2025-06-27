package com.example.milo_be.JWT;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "YourSecretKeyForJwtSigning"; // 서명용 비밀키
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 만료 시간: 2시간

    // JWT 토큰 생성
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId) // 사용자 식별자 저장
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // 서명 알고리즘 및 키 설정
                .compact();
    }

    // JWT 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true; // 파싱 성공 → 유효한 토큰
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 만료되었거나 위조된 토큰
        }
    }

    // 토큰에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // 토큰에 저장된 사용자 ID 반환
    }
}

