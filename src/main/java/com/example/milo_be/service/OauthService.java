package com.example.milo_be.service;

import com.example.milo_be.domain.entity.User;
import com.example.milo_be.dto.KakaoLoginRequestDto;
import com.example.milo_be.dto.LoginResponseDto;
import com.example.milo_be.JWT.JwtUtil;
import com.example.milo_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate = new RestTemplate();

    public LoginResponseDto kakaoLogin(KakaoLoginRequestDto requestDto) {
        String accessToken = requestDto.getAccessToken();

        // 1️⃣ 카카오 서버에 유저 정보 요청
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("카카오 유저 정보 조회 실패");
        }

        Map<String, Object> body = response.getBody();
        Long kakaoId = ((Number) body.get("id")).longValue();
        Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");

        // 1-1️⃣ 닉네임 추출
        String nickname = "카카오유저";
        if (kakaoAccount.get("profile") instanceof Map profile) {
            Object rawNickname = profile.get("nickname");
            if (rawNickname instanceof String nick) {
                nickname = nick;
            }
        }

        // 1-2️⃣ 이메일이 없으면 대체 이메일 생성
        String email = kakaoAccount.get("email") != null
                ? (String) kakaoAccount.get("email")
                : "kakao_" + kakaoId + "@milo.com";

        // 2️⃣ 유저 정보 세팅
        String provider = "kakao";
        String providerId = kakaoId.toString();
        String userId = "kakao_" + providerId;

        // 3️⃣ 기존 유저 조회 또는 자동 가입
        Optional<User> userOpt = userRepository.findByProviderAndProviderId(provider, providerId);
        // 람다 외부에서 final 변수처럼 복사
        final String finalNickname = nickname;
        final String finalEmail = email;
        final String finalUserId = userId;
        final String finalProvider = provider;
        final String finalProviderId = providerId;

        User user = userOpt.orElseGet(() -> {
            User newUser = User.builder()
                    .userId(finalUserId)
                    .email(finalEmail)
                    .nickname(finalNickname)
                    .provider(finalProvider)
                    .providerId(finalProviderId)
                    .createdAt(LocalDateTime.now())
                    .emotionPrompt(0)
                    .build();
            return userRepository.save(newUser);
        });


        // 4️⃣ JWT 발급
        String token = jwtUtil.generateToken(user.getUserId());

        // 5️⃣ 응답
        return new LoginResponseDto(token, user.getUserId(), user.getNickname());
    }
}
