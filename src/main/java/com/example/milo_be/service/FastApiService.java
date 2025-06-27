package com.example.milo_be.service;

import com.example.milo_be.domain.entity.RoleCharacter;
import com.example.milo_be.repository.RoleCharacterRepository;
import com.example.milo_be.dto.RolePlayRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * FastAPI 역할극 대화 연동 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FastApiService {

    private final RestTemplate restTemplate;
    private final RoleCharacterRepository roleCharacterRepository;

    @Value("${fastapi.base-url}")
    private String fastApiBaseUrl;

    // FastAPI에 역할극 대화 요청 보내기
    public String sendChatToFastAPI(String userId, String input) {
        RoleCharacter character = roleCharacterRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalStateException("등록된 역할이 없습니다."));

        RolePlayRequestDto dto = new RolePlayRequestDto();
        dto.setUser_id(userId);
        dto.setCharacter_id(character.getCharacterId());
        dto.setInput(input);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RolePlayRequestDto> request = new HttpEntity<>(dto, headers);

        try {
            String url = fastApiBaseUrl + "/api/roleplay/chat";
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

