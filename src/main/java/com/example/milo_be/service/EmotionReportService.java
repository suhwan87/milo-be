package com.example.milo_be.service;

import com.example.milo_be.domain.entity.DailyEmotionReport;
import com.example.milo_be.domain.entity.User;
import com.example.milo_be.dto.DayEmotionDto;
import com.example.milo_be.dto.EmotionReportResponseDto;
import com.example.milo_be.repository.EmotionReportRepository;
import com.example.milo_be.repository.UserRepository;
import com.example.milo_be.JWT.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 감정 리포트 조회 서비스
 * - 일간 리포트, 월간 감정 날짜/요약 조회
 */
@Service
@RequiredArgsConstructor
public class EmotionReportService {

    private final EmotionReportRepository reportRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // 특정 날짜의 감정 리포트 조회
    public EmotionReportResponseDto.DailyReport getDailyReport(String token, LocalDate date) {
        String userId = jwtUtil.getUserIdFromToken(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 사용자입니다."));

        DailyEmotionReport report = reportRepository.findByUserAndDate(user, date)
                .orElseThrow(() -> new NoSuchElementException("해당 날짜의 리포트가 존재하지 않습니다."));

        return EmotionReportResponseDto.DailyReport.from(report);
    }

    // 특정 월에 리포트가 존재하는 날짜 목록(day 숫자) 조회
    public List<Integer> getReportDaysInMonth(String token, int year, int month) {
        String userId = jwtUtil.getUserIdFromToken(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 사용자입니다."));

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return reportRepository.findReportDaysByUserAndMonth(user, start, end);
    }

    // 특정 월의 날짜별 주요 감정 키워드 목록 조회
    public List<DayEmotionDto> getDayEmotionInMonth(String token, YearMonth ym) {
        String userId = jwtUtil.getUserIdFromToken(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 사용자입니다."));

        LocalDate start = ym.atDay(1);
        LocalDate end   = ym.atEndOfMonth();

        return reportRepository
                .findDayAndEmotionByUserAndMonth(user, start, end);
    }

}
