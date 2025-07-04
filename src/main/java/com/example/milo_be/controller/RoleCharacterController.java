package com.example.milo_be.controller;

import com.example.milo_be.dto.RoleCharacterDto;
import com.example.milo_be.service.RoleCharacterService;
import com.example.milo_be.service.RolePlayLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 역할극 캐릭터 생성/확인/삭제 컨트롤러
 */
@RestController
@RequestMapping("/api/character")
@RequiredArgsConstructor
public class RoleCharacterController {

    private final RoleCharacterService roleCharacterService;
    private final RolePlayLogService rolePlayLogService;

    // 역할극 캐릭터 생성
    @PostMapping
    public ResponseEntity<Long> saveCharacter(@RequestBody RoleCharacterDto dto) {
        Long characterId = roleCharacterService.save(dto);
        return ResponseEntity.ok(characterId);
    }

    // 사용자 캐릭터 등록 여부 확인
    @GetMapping("/{userId}/exists")
    public ResponseEntity<Boolean> checkIfCharacterExists(@PathVariable String userId) {
        boolean exists = roleCharacterService.existsByUserId(userId);
        return ResponseEntity.ok(exists);
    }

    // 역할 및 대화 로그 + FastAPI 세션 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteCharacterAndLogs(@PathVariable String userId) {
        // 내부에서 FastAPI 연동까지 포함된 서비스 호출
        rolePlayLogService.deleteRolePlayDataByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
