package com.example.milo_be.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * role_character_TB 테이블 매핑 엔터티
 * 역할극 캐릭터 정보 저장
 */
@Entity
@Table(name = "role_character_TB")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleCharacter {

    // PK: 캐릭터 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARACTER_ID")
    private Long characterId;

    // FK: 사용자 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // 캐릭터 이름
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    // 사용자와의 관계
    @Column(name = "RELATIONSHIP", nullable = false, length = 50)
    private String relationship;

    // 말투 스타일
    @Column(name = "TONE", nullable = false, length = 50)
    private String tone;

    // 성격 설명
    @Column(name = "PERSONALITY", nullable = false, columnDefinition = "TEXT")
    private String personality;

    // 역할극 상황 설명
    @Column(name = "SITUATION", nullable = false, columnDefinition = "TEXT")
    private String situation;

    // 생성 시간
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
