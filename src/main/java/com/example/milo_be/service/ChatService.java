package com.example.milo_be.service;

import com.example.milo_be.JWT.JwtUtil;
import com.example.milo_be.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 채팅
     */
    public ChatDto.ChatResponse processChat(String token, String message) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
        String userId = jwtUtil.getUserIdFromToken(jwt);
        String promptType = userService.getPromptType(userId);

        System.out.println("🔑 [processChat] JWT: " + jwt);
        System.out.println("👤 [processChat] userId: " + userId);

        Map<String, String> payload = new HashMap<>();
        payload.put("user_id", userId);
        payload.put("input", message);
        payload.put("persona", promptType);

        System.out.println("📦 [processChat] FastAPI 요청 페이로드: " + payload);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        String fastApiUrl = "http://192.168.219.48:8000/api/chat/";
        System.out.println("🌐 [processChat] FastAPI POST 요청 → " + fastApiUrl);

        try {
            ResponseEntity<ChatDto.ChatResponse> response =
                    restTemplate.postForEntity(fastApiUrl, entity, ChatDto.ChatResponse.class);

            if (response.getBody() == null) {
                System.out.println("❗ [processChat] FastAPI 응답 body가 null입니다. 상태 코드: " + response.getStatusCode());
                throw new RuntimeException("FastAPI 응답 body가 null입니다.");
            }

            String botReply = response.getBody().getOutput();
            System.out.println("🤖 [processChat] FastAPI 응답 메시지: " + botReply);

            return response.getBody();

        } catch (HttpClientErrorException e) {
            System.out.println("🚫 [processChat] FastAPI 요청 실패 - 클라이언트 오류 (4xx)");
            System.out.println("상태 코드: " + e.getStatusCode());
            System.out.println("응답 바디: " + e.getResponseBodyAsString());
            e.printStackTrace();
            throw new RuntimeException("FastAPI 오류(클라이언트): " + e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            System.out.println("🔥 [processChat] FastAPI 요청 실패 - 서버 오류 (5xx)");
            System.out.println("상태 코드: " + e.getStatusCode());
            System.out.println("응답 바디: " + e.getResponseBodyAsString());
            e.printStackTrace();
            throw new RuntimeException("FastAPI 오류(서버): " + e.getResponseBodyAsString());

        } catch (Exception e) {
            System.out.println("💥 [processChat] FastAPI 요청 실패 - 알 수 없는 예외");
            e.printStackTrace();
            throw new RuntimeException("FastAPI 오류(예외): " + e.getMessage());
        }
    }


    /**
     * 채팅 종료 및 리포트 요청
     */
    public void endChat(String token) {
        System.out.println("✅ [endChat] 채팅 종료 요청 시작");

        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
        System.out.println("🔑 [endChat] 파싱된 JWT: " + jwt);

        String userId = jwtUtil.getUserIdFromToken(jwt);
        System.out.println("👤 [endChat] 추출된 userId: " + userId);

        // ✅ user_id를 쿼리 파라미터로 전송
        String url = "http://192.168.219.48:8000/api/session/end?user_id=" + userId;
        System.out.println("🌐 [endChat] 호출할 FastAPI URL: " + url);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            System.out.println("✅ [endChat] FastAPI 응답 상태: " + response.getStatusCode());
            System.out.println("✅ [endChat] FastAPI 응답 내용: " + response.getBody());
        } catch (Exception e) {
            System.out.println("❌ [endChat] FastAPI 호출 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}