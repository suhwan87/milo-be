package com.example.milo_be.service;

import com.example.milo_be.dto.RoleCharacterDto;
import com.example.milo_be.dto.RolePlayRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class FastApiService {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "http://192.168.219.48:8000/api/roleplay";

    /**
     * 역할 정보 FastAPI에 전달 (assign)
     */
    public void assignCharacterToFastAPI(RoleCharacterDto dto) {
        String url = BASE_URL + "/assign";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RoleCharacterDto> request = new HttpEntity<>(dto, headers);

        try {
            restTemplate.postForEntity(url, request, String.class);
            log.info("✅ 역할 정보 FastAPI로 전달 완료");
        } catch (Exception e) {
            log.error("❌ 역할 정보 전달 실패", e);
        }
    }

    /**
     * 역할 기반 대화 요청 (chat)
     */
    public String sendChatToFastAPI(String userId, Long characterId, String input) {
        String url = BASE_URL + "/chat";

        RolePlayRequestDto dto = new RolePlayRequestDto();
        dto.setUser_id(userId);
        dto.setCharacter_id(characterId);
        dto.setInput(input);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RolePlayRequestDto> request = new HttpEntity<>(dto, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode outputNode = root.get("output");

            return outputNode != null ? outputNode.asText() : "응답 텍스트를 불러오지 못했습니다.";

        } catch (Exception e) {
            log.error("❌ 역할극 대화 요청 실패", e);
            return "역할극 응답 처리 중 오류가 발생했어요.";
        }
    }
}
