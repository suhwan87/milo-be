package com.example.milo_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 로그인 요청 DTO
 * - userId: 사용자 ID
 * - password: 비밀번호
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
    private String userId;
    private String password;
}