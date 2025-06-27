package com.example.milo_be.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * monthly_emotion_summary_TB 테이블 매핑 엔터티
 * 사용자 월별 감정 요약 정보 저장
 */
@Entity
@Table(name = "monthly_emotion_summary_TB",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "YEAR_MONTHS"})})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyEmotionSummary {

    // PK: 요약 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUMMARY_ID")
    private Long summaryId;

    // FK: 사용자 ID
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // 대상 연월 (예: 2024-06-01)
    @Column(name = "YEAR_MONTHS", nullable = false)
    private LocalDate yearMonths;

    // 감정 세션 총 횟수
    @Column(name = "TOTAL_SESSIONS", nullable = false)
    private int totalSessions = 0;

    // 감정 평균값 (월 기준)
    @Column(name = "AVG_STABLE", nullable = false)
    private float avgStable;

    @Column(name = "AVG_JOY", nullable = false)
    private float avgJoy;

    @Column(name = "AVG_SADNESS", nullable = false)
    private float avgSadness;

    @Column(name = "AVG_ANGER", nullable = false)
    private float avgAnger;

    @Column(name = "AVG_ANXIETY", nullable = false)
    private float avgAnxiety;

    // 월별 대표 감정
    @Column(name = "DOMINANT_EMOTION", length = 20)
    private String dominantEmotion;

    // GPT가 생성한 감정 피드백
    @Column(name = "GPT_FEEDBACK", columnDefinition = "TEXT")
    private String gptFeedback;

    // 생성 시간
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정 시간 (갱신 시 자동 업데이트)
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
