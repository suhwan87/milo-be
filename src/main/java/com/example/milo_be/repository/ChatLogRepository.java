package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
    // 사용자 ID에 해당하는 모든 데이터를 삭제
    void deleteByUser_UserId(String userId);
}