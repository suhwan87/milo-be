package com.example.milo_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 정보 응답 DTO
 * - 마이페이지, 로그인 이후 사용자 정보 전달
 */
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String userId;
    private String nickname;
    private String email;
}
