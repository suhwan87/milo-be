package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.DailyEmotionReport;
import com.example.milo_be.domain.entity.User;
import com.example.milo_be.dto.DayEmotionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * DailyEmotionReport 엔터티 전용 JPA Repository
 */
@Repository
public interface EmotionReportRepository extends JpaRepository<DailyEmotionReport, Long> {
    // 사용자 ID에 해당하는 모든 데이터를 삭제
    void deleteByUser_UserId(String userId);

    // 특정 사용자와 날짜로 리포트를 조회
    Optional<DailyEmotionReport> findByUserAndDate(User user, LocalDate date);

    // 해당 사용자가 리포트를 한 번이라도 작성했는지 여부
    boolean existsByUser(User user);

    // 해당 사용자가 오늘 리포트를 작성했는지 여부
    boolean existsByUserAndDate(User user, LocalDate date);

    // 특정 월에 해당하는 날짜(day) 목록 조회
    @Query("SELECT DAY(r.date) " +
            "FROM DailyEmotionReport r " +
            "WHERE r.user = :user " +
            "AND r.date BETWEEN :startDate AND :endDate")
    List<Integer> findReportDaysByUserAndMonth(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 특정 월의 날짜 + 주요 감정 키워드 목록 조회
    @Query("""
   SELECT NEW com.example.milo_be.dto.DayEmotionDto(r.date, r.mainEmotion)
   FROM DailyEmotionReport r
   WHERE r.user = :user
     AND r.date BETWEEN :startDate AND :endDate
   ORDER BY r.date DESC
""")
    List<DayEmotionDto> findDayAndEmotionByUserAndMonth(
            @Param("user")      User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate")   LocalDate endDate);
}
