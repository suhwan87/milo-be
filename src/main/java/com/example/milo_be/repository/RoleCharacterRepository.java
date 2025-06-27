package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.RoleCharacter;
import com.example.milo_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * RoleCharacter 엔터티 전용 JPA Repository
 */
public interface RoleCharacterRepository extends JpaRepository<RoleCharacter, Long> {
    // 특정 사용자에 대한 역할 캐릭터 조회
    Optional<RoleCharacter> findByUser(User user);

    // 사용자 ID 기준 역할 캐릭터 존재 여부 확인
    boolean existsByUser_UserId(String userId);

    // 사용자 ID 기준 역할 캐릭터 조회
    Optional<RoleCharacter> findByUser_UserId(String userId);

    // 사용자 ID에 해당하는 모든 데이터를 삭제
    void deleteByUser_UserId(String userId);

}
