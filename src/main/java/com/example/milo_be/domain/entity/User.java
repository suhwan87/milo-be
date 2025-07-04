package com.example.milo_be.domain.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * users_TB 테이블 매핑 엔터티
 * 사용자 기본 정보 저장
 */
@Entity
@Table(name = "users_TB")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // PK: 사용자 ID (문자열 ID 사용)
    @Id
    @Column(name = "USER_ID", length = 50)
    private String userId;

    // 비밀번호 (암호화 저장됨)
    @Setter
    @Column(name = "PASSWORD", nullable = true, length = 100)
    private String password; // 일반 로그인 유저만 사용, 소셜 로그인은 null 가능

    // 닉네임 (변경 가능)
    @Setter
    @Column(name = "NICKNAME", nullable = false, length = 50)
    private String nickname;

    // 이메일 (유일값)
    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    // 가입일시 (자동 생성)
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 챗봇 응답 스타일 설정 (0: 공감형, 1: 조언형)
    @Setter
    @Column(name = "EMOTION_PROMPT")
    private Integer emotionPrompt;

    // ✅ 로그인 제공자 정보 (local, kakao 등)
    @Column(name = "PROVIDER", length = 20)
    private String provider;

    // ✅ 소셜 로그인 제공자의 고유 ID (카카오 ID 등)
    @Column(name = "PROVIDER_ID", length = 100)
    private String providerId;
}
