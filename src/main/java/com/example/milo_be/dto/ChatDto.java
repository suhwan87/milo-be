package com.example.milo_be.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 챗봇 대화용 요청/응답 DTO
 */
public class ChatDto {

    // 클라이언트 → 서버 : 사용자 메시지 전달용 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ChatRequest {
        private String message; // 사용자 입력 메시지
    }

    // 서버 → 클라이언트 : 챗봇 응답 전달용 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ChatResponse {
        private String output; // 챗봇 출력 메시지
    }
}