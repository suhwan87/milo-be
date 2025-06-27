package com.example.milo_be.domain.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * chat_log_TB 테이블 매핑 엔터티
 * 사용자와 챗봇 간의 1건 대화 기록 저장용
 */
@Entity
@Table(name = "chat_log_TB")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatLog {

    // PK: 대화 ID (자동 증가)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ID")
    private Long chatId;

    // FK: 사용자 ID (User 엔터티와 다대일 관계)
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // 사용자 메시지 (텍스트)
    @Column(name = "SENDER", nullable = false, columnDefinition = "TEXT")
    private String sender;

    // 챗봇 응답 메시지 (텍스트)
    @Column(name = "RESPONDER", nullable = false, columnDefinition = "TEXT")
    private String responder;

    // 생성 시간 (자동 입력)
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

