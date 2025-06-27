package com.example.milo_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자 정보 요청 DTO
 * - 회원가입 및 정보 수정에 사용
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String userId;
    private String password;
    private String nickname;
    private String email;
    private Integer emotionPrompt; // 챗봇 스타일 (0: 공감형, 1: 조언형)
}