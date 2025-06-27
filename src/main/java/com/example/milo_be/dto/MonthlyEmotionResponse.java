package com.example.milo_be.dto;

import com.example.milo_be.domain.entity.MonthlyEmotionSummary;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 월별 감정 리포트 응답 DTO
 * - 평균 감정 값, 대표 감정, GPT 피드백 포함
 */
@Getter
@AllArgsConstructor
public class MonthlyEmotionResponse {
    private float avgStable;
    private float avgJoy;
    private float avgSadness;
    private float avgAnger;
    private float avgAnxiety;
    private String dominantEmotion;
    private String gptFeedback;

    // 엔터티 → DTO 변환 메서드
    public static MonthlyEmotionResponse from(MonthlyEmotionSummary entity) {
        return new MonthlyEmotionResponse(
                entity.getAvgStable(),
                entity.getAvgJoy(),
                entity.getAvgSadness(),
                entity.getAvgAnger(),
                entity.getAvgAnxiety(),
                entity.getDominantEmotion(),
                entity.getGptFeedback()
        );
    }
}