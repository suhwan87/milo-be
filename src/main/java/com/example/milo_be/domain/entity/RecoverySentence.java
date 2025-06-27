package com.example.milo_be.domain.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * recovery_sentence_TB 테이블 매핑 엔터티
 * 사용자 회복 문장 저장 (폴더 단위)
 */
@Entity
@Table(name = "recovery_sentence_TB")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoverySentence {

    // PK: 문장 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SENTENCE_ID")
    private Long sentenceId;

    // FK: 사용자 ID
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // FK: 소속 폴더
    @ManyToOne
    @JoinColumn(name = "FOLDER_ID", nullable = false)
    private RecoveryFolder folder;

    // 회복 문장 내용
    @Setter
    @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
    private String content;

    // 생성 시간
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
