package com.example.milo_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 리포트 상태 응답 DTO
 * - 신규 유저 여부 및 리포트 존재 여부 전달
 */
@Getter
@AllArgsConstructor
public class UserReportStatusDto {
    private boolean isNewUser;
    private boolean hasAnyReport;
    private boolean hasTodayReport;
}
