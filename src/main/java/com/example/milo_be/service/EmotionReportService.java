package com.example.milo_be.service;

import com.example.milo_be.domain.entity.DailyEmotionReport;
import com.example.milo_be.domain.entity.User;
import com.example.milo_be.dto.EmotionReportResponseDto;
import com.example.milo_be.repository.EmotionReportRepository;
import com.example.milo_be.repository.UserRepository;
import com.example.milo_be.JWT.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmotionReportService {

    private final EmotionReportRepository reportRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * 특정 날짜의 감정 리포트를 반환하는 서비스 로직
     */
    public EmotionReportResponseDto.DailyReport getDailyReport(String token, LocalDate date) {
        String userId = jwtUtil.getUserIdFromToken(token); // 토큰에서 userId 추출
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 사용자입니다."));

        DailyEmotionReport report = reportRepository.findByUserAndDate(user, date)
                .orElseThrow(() -> new NoSuchElementException("해당 날짜의 리포트가 존재하지 않습니다."));

        return EmotionReportResponseDto.DailyReport.from(report);
    }
}
