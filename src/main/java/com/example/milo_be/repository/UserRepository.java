package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User 엔터티 전용 JPA Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 이메일 중복 여부 확인
    boolean existsByEmail(String email);

    // 아이디 찾기: 닉네임 + 이메일 → userId 조회
    Optional<User> findByNicknameAndEmail(String nickname, String email);

    // 비밀번호 찾기: 닉네임 + 이메일 + userId → 사용자 존재 여부 확인
    Optional<User> findByNicknameAndUserIdAndEmail(String nickname, String userId, String email);

}
