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

@Service
@RequiredArgsConstructor
public class ChatService {

    private final JwtUtil jwtUtil;
    private final ChatStyleService chatStyleService;
    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ 환경 변수에서 base-url 주입
    @Value("${fastapi.base-url}")
    private String fastApiBaseUrl;

    /**
     * 채팅 첫 진입 인사
     */
    public ChatDto.ChatResponse getInitialGreeting(String userId) {
        String fastApiUrl = fastApiBaseUrl + "/api/chat/init?user_id=" + userId;
        System.out.println("🌐 [getInitialGreeting] FastAPI GET 요청 → " + fastApiUrl);

        try {
            ResponseEntity<ChatDto.ChatResponse> response =
                    restTemplate.getForEntity(fastApiUrl, ChatDto.ChatResponse.class);

            if (response.getBody() == null) {
                System.out.println("❗ [getInitialGreeting] FastAPI 응답 body가 null입니다.");
                throw new RuntimeException("FastAPI 응답 body가 null입니다.");
            }

            System.out.println("🤖 [getInitialGreeting] FastAPI 응답 메시지: " + response.getBody().getOutput());
            return response.getBody();

        } catch (Exception e) {
            System.out.println("💥 [getInitialGreeting] FastAPI 요청 실패");
            throw new RuntimeException("FastAPI 오류: " + e.getMessage());
        }
    }

    /**
     * 채팅
     */
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

            System.out.println("📤 [Chat 요청] userId: " + userId + ", message: " + message);
            System.out.println("🤖 [FastAPI 응답] " + response.getBody().getOutput());

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("❌ FastAPI 예외: " + e.getMessage());
        }
    }

    /**
     * 채팅 종료 시 리포트 요청
     */
    @Async
    public void endChat(String token) {
        System.out.println("✅ [endChat] 일일 감정 리포트 저장 및 월간 요약 요청 시작");

        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
        String userId = jwtUtil.getUserIdFromToken(jwt);
        System.out.println("👤 [endChat] 추출된 userId: " + userId);

        // ✅ Step 1: 채팅 종료 요청
        String endChatUrl = fastApiBaseUrl + "/api/session/end?user_id=" + userId;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(endChatUrl, null, String.class);
            System.out.println("✅ [endChat] 일일 분석 리포트 요청 완료: " + response.getStatusCode());
        } catch (Exception e) {
            System.out.println("❌ [endChat] 일일 분석 리포트 요청 실패: " + e.getMessage());
        }

        // ✅ Step 2: 월간 요약 요청
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
            System.out.println("📦 [endChat] 월간 요약 요청 응답: " + summaryResponse.getStatusCode());
            System.out.println("📄 [endChat] 월간 요약 응답 본문: " + summaryResponse.getBody());

        } catch (Exception e) {
            System.out.println("❌ [endChat] 월간 요약 요청 실패: " + e.getMessage());
        }
    }
}
