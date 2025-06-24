package com.example.milo_be.controller;

import com.example.milo_be.JWT.JwtUtil;
import com.example.milo_be.dto.CharStyleRequestDto;
import com.example.milo_be.service.CharStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prompt")
@RequiredArgsConstructor
public class CharStyleController {

    private final CharStyleService promptService;
    private final JwtUtil jwtUtil;

    /**
     * 챗봇 대화 스타일 조회
     */
    @GetMapping
    public ResponseEntity<String> getPrompt(@RequestHeader("Authorization") String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
        String userId = jwtUtil.getUserIdFromToken(jwt);

        String promptType = promptService.getPromptType(userId);
        return ResponseEntity.ok(promptType);
    }

    /**
     * 챗봇 대화 스타일 변경
     */
    @PutMapping
    public ResponseEntity<Void> updatePrompt(
            @RequestHeader("Authorization") String token,
            @RequestBody CharStyleRequestDto request
    ) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
        String userId = jwtUtil.getUserIdFromToken(jwt);

        promptService.updatePrompt(userId, request.getEmotionPrompt());
        return ResponseEntity.ok().build();
    }
}