package com.example.milo_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 챗봇 스타일 설정 요청 DTO
 * emotionPrompt: 0 (공감형), 1 (조언형)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatStyleRequestDto {
    private Integer emotionPrompt; // 챗봇 스타일 설정값
}
