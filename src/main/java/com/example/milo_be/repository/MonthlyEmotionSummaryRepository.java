package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.MonthlyEmotionSummary;
import com.example.milo_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * MonthlyEmotionSummary 엔터티 전용 JPA Repository
 */
public interface MonthlyEmotionSummaryRepository extends JpaRepository<MonthlyEmotionSummary, Long> {
    // 특정 사용자와 연월에 해당하는 감정 요약 조회
    Optional<MonthlyEmotionSummary> findByUserAndYearMonths(User user, LocalDate yearMonths);

    // 사용자 ID에 해당하는 모든 데이터를 삭제
    void deleteByUser_UserId(String userId);
}
