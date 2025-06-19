package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.DailyEmotionReport;
import com.example.milo_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EmotionReportRepository extends JpaRepository<DailyEmotionReport, Long> {

    /**
     * 특정 사용자와 날짜로 리포트를 조회
     */
    Optional<DailyEmotionReport> findByUserAndDate(User user, LocalDate date);
}
