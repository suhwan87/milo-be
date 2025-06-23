package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.DailyEmotionReport;
import com.example.milo_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmotionReportRepository extends JpaRepository<DailyEmotionReport, Long> {

    /**
     * 특정 사용자와 날짜로 리포트를 조회
     */
    Optional<DailyEmotionReport> findByUserAndDate(User user, LocalDate date);

    /**
     * ✅ 사용자별 특정 월의 리포트 날짜(day 숫자) 목록 조회
     */
    @Query("SELECT DAY(r.date) " +
            "FROM DailyEmotionReport r " +
            "WHERE r.user = :user " +
            "AND r.date BETWEEN :startDate AND :endDate")
    List<Integer> findReportDaysByUserAndMonth(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
