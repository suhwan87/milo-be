package com.example.milo_be.service;

import com.example.milo_be.JWT.JwtUtil;
import com.example.milo_be.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 챗봇 대화 처리 서비스
 * - FastAPI 연동
 * - 사용자 스타일 기반 응답 구성
 * - 대화 종료 시 감정 리포트 저장 트리거
 */
@Service
@RequiredArgsConstructor
public class ChatService {

    private final JwtUtil jwtUtil;
    private final ChatStyleService chatStyleService;
    private final RestTemplate restTemplate = new RestTemplate();

    // FastAPI 서버 주소 주입
    @Value("${fastapi.base-url}")
    private String fastApiBaseUrl;

    // 챗봇 첫 인삿말 요청
    public ChatDto.ChatResponse getInitialGreeting(String userId) {
        String fastApiUrl = fastApiBaseUrl + "/api/chat/init?user_id=" + userId;
        System.out.println("[getInitialGreeting] FastAPI GET 요청 → " + fastApiUrl);

        try {
            ResponseEntity<ChatDto.ChatResponse> response =
                    restTemplate.getForEntity(fastApiUrl, ChatDto.ChatResponse.class);

            if (response.getBody() == null) {
                throw new RuntimeException("FastAPI 응답 body가 null입니다.");
            }

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("FastAPI 오류: " + e.getMessage());
        }
    }

    // 사용자 메시지에 대한 챗봇 응답 처리
    public ChatDto.ChatResponse processChat(String token, String message) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
        String userId = jwtUtil.getUserIdFromToken(jwt);
        String promptType = chatStyleService.getPromptType(userId);

        Map<String, String> payload = new HashMap<>();
        payload.put("user_id", userId);
        payload.put("input", message);
        payload.put("persona", promptType);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        String fastApiUrl = fastApiBaseUrl + "/api/chat/";

        try {
            ResponseEntity<ChatDto.ChatResponse> response =
                    restTemplate.postForEntity(fastApiUrl, entity, ChatDto.ChatResponse.class);

            if (response.getBody() == null) {
                throw new RuntimeException("FastAPI 응답 body가 null입니다.");
            }

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("FastAPI 예외: " + e.getMessage());
        }
    }

    // 채팅 종료 시 리포트 요청
    @Async
    public void endChat(String token) {
        System.out.println("[endChat] 일일 감정 리포트 저장 및 월간 요약 요청 시작");

        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
        String userId = jwtUtil.getUserIdFromToken(jwt);

        // 일일 리포트 생성 요청
        String endChatUrl = fastApiBaseUrl + "/api/session/end?user_id=" + userId;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(endChatUrl, null, String.class);
        } catch (Exception e) {
            System.out.println("[endChat] 일일 분석 리포트 요청 실패: " + e.getMessage());
        }

        // 월간 요약 생성 요청
        try {
            LocalDate now = LocalDate.now();
            int year = now.getYear();
            int month = now.getMonthValue();

            String summaryUrl = String.format(
                    fastApiBaseUrl + "/api/reports/monthly/%d/%d", year, month
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = Map.of("user_id", userId);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> summaryResponse = restTemplate.postForEntity(summaryUrl, request, String.class);
            System.out.println("[endChat] 월간 요약 요청 응답: " + summaryResponse.getStatusCode());
            System.out.println("[endChat] 월간 요약 응답 본문: " + summaryResponse.getBody());

        } catch (Exception e) {
            System.out.println("[endChat] 월간 요약 요청 실패: " + e.getMessage());
        }
    }
}
