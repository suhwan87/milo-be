package com.example.milo_be.controller;

import com.example.milo_be.JWT.JwtUtil;
import com.example.milo_be.dto.ChatDto;
import com.example.milo_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(
            @RequestHeader("Authorization") String token,
            @RequestBody ChatDto.ChatRequest request
    ) {
        try {
            System.out.println("✅ ChatController 도착");
            System.out.println("Authorization 헤더: " + token);

            // 1. 토큰 파싱
            String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
            String userId = jwtUtil.getUserIdFromToken(jwt);

            // 2. 요청 메시지 파싱
            String message = request.getMessage();
            String promptType = userService.getPromptType(userId);

            System.out.println("✅ 수신 메시지: " + message);
            System.out.println("✅ userId: " + userId);
            System.out.println("✅ promptType: " + promptType);

            // 3. FastAPI 요청 페이로드 구성
            Map<String, String> fastApiPayload = new HashMap<>();
            fastApiPayload.put("user_id", userId);
            fastApiPayload.put("input", message);
            fastApiPayload.put("persona", promptType);

            System.out.println("📦 FastAPI 요청 페이로드: " + fastApiPayload);

            // 4. 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(fastApiPayload, headers);

            // 5. FastAPI 호출
            String fastApiUrl = "http://192.168.219.48:8000/api/chat/";
            System.out.println("🌐 FastAPI로 POST 요청 중: " + fastApiUrl);

            ResponseEntity<ChatDto.ChatResponse> response = restTemplate.postForEntity(
                    fastApiUrl, entity, ChatDto.ChatResponse.class);

            // ✅ NPE 방지: null 체크 추가
            if (response.getBody() == null) {
                System.out.println("❗ FastAPI 응답 body가 null입니다. 상태 코드: " + response.getStatusCode());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("FastAPI 응답 body가 null입니다. 상태 코드: " + response.getStatusCode());
            }

            String botReply = response.getBody().getOutput();
            System.out.println("🤖 FastAPI 응답: " + botReply);

            return ResponseEntity.ok(response.getBody());

        } catch (HttpClientErrorException e) {
            System.out.println("🚫 FastAPI 요청 실패 - 클라이언트 오류 (4xx)");
            System.out.println("상태 코드: " + e.getStatusCode());
            System.out.println("응답 바디: " + e.getResponseBodyAsString());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("FastAPI 오류(클라이언트): " + e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            System.out.println("🔥 FastAPI 요청 실패 - 서버 오류 (5xx)");
            System.out.println("상태 코드: " + e.getStatusCode());
            System.out.println("응답 바디: " + e.getResponseBodyAsString());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("FastAPI 오류(서버): " + e.getResponseBodyAsString());

        } catch (Exception e) {
            System.out.println("💥 FastAPI 요청 실패 - 알 수 없는 오류");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("FastAPI 오류(예외): " + e.getMessage());
        }}
}