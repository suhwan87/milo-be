package com.example.milo_be.service;

import com.example.milo_be.domain.entity.RoleCharacter;
import com.example.milo_be.domain.entity.RolePlayLog;
import com.example.milo_be.dto.RolePlayLogDto;
import com.example.milo_be.repository.RoleCharacterRepository;
import com.example.milo_be.repository.RolePlayLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 역할극 대화 로그 조회 및 종료 처리 서비스
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RolePlayLogService {

    private final RolePlayLogRepository rolePlayLogRepository;
    private final RoleCharacterRepository roleCharacterRepository;

    @Value("${fastapi.base-url}")
    private String fastApiBaseUrl;

    private final RestTemplate restTemplate;

    // 사용자 ID 기반으로 역할극 대화 로그 전체 조회
    public List<RolePlayLogDto> fetchLogs(String userId) {
        List<RolePlayLog> logs = rolePlayLogRepository.findByUser_UserIdOrderByCreatedAtAsc(userId);

        return logs.stream()
                .map(log -> new RolePlayLogDto(
                        log.getSender(),
                        log.getResponder(),
                        log.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ))
                .collect(Collectors.toList());
    }

    // 역할극 종료 시, 해당 유저의 대화 로그와 역할 정보 삭제
    public void deleteRolePlayDataByUserId(String userId) {
        // 1. 사용자에 대한 역할 정보 조회
        RoleCharacter character = roleCharacterRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 역할이 없습니다."));

        Long characterId = character.getCharacterId();

        // 2. Spring Boot DB에서 역할 정보 삭제 (cascade로 대화 로그 삭제)
        roleCharacterRepository.delete(character);

        // 3. FastAPI로 세션 초기화 요청 전송
        String url = fastApiBaseUrl + "/api/roleplay/reset";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("user_id", userId)
                .queryParam("character_id", characterId);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(builder.toUriString(), null, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("✅ FastAPI 역할극 세션 초기화 성공");
            } else {
                log.warn("⚠️ FastAPI 응답 상태 코드: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("❌ FastAPI 역할 초기화 요청 실패", e);
        }
    }

}

