package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.RolePlayLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePlayLogRepository extends JpaRepository<RolePlayLog, Long> {

    // 사용자 ID를 기반으로 대화 로그 조회
    List<RolePlayLog> findByUser_UserIdOrderByCreatedAtAsc(String userId);

    // 사용자 ID에 해당하는 모든 데이터를 삭제
    void deleteByUser_UserId(String userId);
}
