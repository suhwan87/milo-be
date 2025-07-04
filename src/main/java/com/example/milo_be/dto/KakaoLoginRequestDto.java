package com.example.milo_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카카오 로그인 요청 DTO
 */

@Getter
@Setter
@NoArgsConstructor
public class KakaoLoginRequestDto {
    private String accessToken;
}
