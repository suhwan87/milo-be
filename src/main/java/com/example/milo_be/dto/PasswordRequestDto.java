package com.example.milo_be.dto;

import lombok.Getter;

/**
 * 비밀번호 변경 요청 DTO
 */
@Getter
public class PasswordRequestDto {
    private String currentPassword;
    private String newPassword;
}