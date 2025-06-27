package com.example.milo_be.domain.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * role_play_log_TB 테이블 매핑 엔터티
 * 역할극 대화 로그 저장
 */
@Entity
@Table(name = "role_play_log_TB")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePlayLog {

    // PK: 역할극 채팅 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_CHAT_ID")
    private Long roleChatId;

    // FK: 사용자 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // FK: 역할 캐릭터 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHARACTER_ID", nullable = false)
    private RoleCharacter roleCharacter;

    // 사용자 메시지
    @Column(name = "SENDER", nullable = false, columnDefinition = "TEXT")
    private String sender;

    // 캐릭터 응답 메시지
    @Column(name = "RESPONDER", nullable = false, columnDefinition = "TEXT")
    private String responder;

    // 생성 시간
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
