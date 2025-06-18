package com.example.milo_be.service;

import com.example.milo_be.domain.entity.RoleCharacter;
import com.example.milo_be.domain.entity.User;
import com.example.milo_be.dto.RoleCharacterDto;
import com.example.milo_be.repository.RoleCharacterRepository;
import com.example.milo_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleCharacterService {

    private final RoleCharacterRepository roleCharacterRepository;
    private final UserRepository userRepository;
    private final FastApiService fastApiService; // ✅ FastAPI 연동 서비스 주입

    public Long save(RoleCharacterDto dto) {
        // 1. 사용자 조회
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 2. 역할 생성 및 저장
        RoleCharacter character = RoleCharacter.builder()
                .user(user)
                .name(dto.getName())
                .relationship(dto.getRelationship())
                .tone(dto.getTone())
                .personality(dto.getPersonality())
                .situation(dto.getSituation())
                .build();

        Long characterId = roleCharacterRepository.save(character).getCharacterId();

        // 3. FastAPI로 역할 정보 전송 (assign endpoint)
        try {
            fastApiService.assignCharacterToFastAPI(dto);
        } catch (Exception e) {
            // FastAPI와의 연동 실패는 치명적이지 않으므로 로깅만
            System.err.println("FastAPI assign 실패: " + e.getMessage());
        }

        return characterId;
    }
}
