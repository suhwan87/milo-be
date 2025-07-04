package com.example.milo_be.controller;

import com.example.milo_be.dto.KakaoLoginRequestDto;
import com.example.milo_be.dto.LoginResponseDto;
import com.example.milo_be.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {

    private final OauthService oauthService;

    /**
     * 카카오 로그인 처리 엔드포인트
     * - 프론트에서 받은 accessToken을 바탕으로 사용자 정보 조회
     * - 신규 유저는 자동 가입 처리
     * - JWT 토큰, userId, nickname 응답
     */
    @PostMapping("/kakao")
    public ResponseEntity<LoginResponseDto> kakaoLogin(@RequestBody KakaoLoginRequestDto requestDto) {
        LoginResponseDto response = oauthService.kakaoLogin(requestDto);
        return ResponseEntity.ok(response);
    }
}
