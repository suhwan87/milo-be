package com.example.milo_be.controller;

import com.example.milo_be.dto.EmotionReportResponseDto;
import com.example.milo_be.service.EmotionReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class EmotionReportController {

    private final EmotionReportService reportService;

    /**
     * [GET] 하루 감정 리포트 조회
     * @param token Authorization 헤더의 JWT 토큰 (Bearer 생략 가능)
     * @param date 조회하고 싶은 날짜 (예: 2025-06-19)
     * @return DailyReport DTO or 404/500 에러 응답
     */
    @GetMapping("/daily")
    public ResponseEntity<?> getDailyReport(
            @RequestHeader("Authorization") String token,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        try {
            String jwt = token.replace("Bearer", "").trim();
            EmotionReportResponseDto.DailyReport response = reportService.getDailyReport(jwt, date);
            return ResponseEntity.ok(response);

        } catch (NoSuchElementException e) {
            // 리포트 또는 사용자 없을 때
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            // 기타 서버 오류
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
}
