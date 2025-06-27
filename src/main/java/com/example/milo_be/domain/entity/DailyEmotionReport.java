package com.example.milo_be.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * daily_emotion_report_TB 테이블 매핑 엔터티
 * 사용자별 하루 감정 분석 결과 저장
 */
@Entity
@Table(name = "daily_emotion_report_TB",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "DATE"})})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyEmotionReport {

    // PK: 리포트 ID (자동 증가)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long reportId;

    // FK: 사용자 ID
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // 분석 날짜 (yyyy-MM-dd)
    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    // 주요 감정 키워드 (예: 기쁨, 불안 등)
    @Column(name = "MAIN_EMOTION", nullable = false, length = 20)
    private String mainEmotion;

    // 전체 감정 점수 (종합 판단 기준)
    @Column(name = "SCORE", nullable = false)
    private float score;

    // 감정 구성 요소 (정규화된 값들)
    @Column(name = "STABLE", nullable = false)
    private float stable;

    @Column(name = "JOY", nullable = false)
    private float joy;

    @Column(name = "SADNESS", nullable = false)
    private float sadness;

    @Column(name = "ANGER", nullable = false)
    private float anger;

    @Column(name = "ANXIETY", nullable = false)
    private float anxiety;

    // GPT 기반 요약 텍스트
    @Column(name = "SUMMARY", nullable = false, columnDefinition = "TEXT")
    private String summary;

    // 사용자 맞춤 피드백
    @Column(name = "FEEDBACK", nullable = false, columnDefinition = "TEXT")
    private String feedback;

    // 응원의 한마디
    @Column(name = "ENCOURAGEMENT", nullable = false, columnDefinition = "TEXT")
    private String encouragement;

    // 생성 일시 (자동 입력)
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
