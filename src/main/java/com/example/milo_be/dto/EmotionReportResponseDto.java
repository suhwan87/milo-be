package com.example.milo_be.dto;

import com.example.milo_be.domain.entity.DailyEmotionReport;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 하루 감정 리포트 응답 DTO
 * - DailyReport 내부 클래스 포함
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmotionReportResponseDto {
    private DailyReport report; // 감정 리포트 본문 응답 필드

    // 실제 감정 리포트 정보 전달용 내부 DTO
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyReport {
        private LocalDate date;
        private LocalDateTime createdAt;
        private String mainEmotion;
        private float score;
        private float stable;
        private float joy;
        private float sadness;
        private float anger;
        private float anxiety;
        private String summary;
        private String feedback;
        private String encouragement;

        // 엔터티 → DTO 변환 메서드
        public static DailyReport from(DailyEmotionReport report) {
            return DailyReport.builder()
                    .date(report.getDate())
                    .createdAt(report.getCreatedAt())
                    .mainEmotion(report.getMainEmotion())
                    .score(report.getScore())
                    .stable(report.getStable())
                    .joy(report.getJoy())
                    .sadness(report.getSadness())
                    .anger(report.getAnger())
                    .anxiety(report.getAnxiety())
                    .summary(report.getSummary())
                    .feedback(report.getFeedback())
                    .encouragement(report.getEncouragement())
                    .build();
        }
    }
}
