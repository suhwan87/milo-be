package com.example.milo_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 응답 DTO
 * - JWT 토큰 및 사용자 ID 반환
 */
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String token;   // JWT 토큰
    private String userId;  // 사용자 ID
    private String nickname; // 닉네임
}
