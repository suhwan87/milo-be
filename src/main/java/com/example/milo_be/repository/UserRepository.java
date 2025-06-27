package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User 엔터티 전용 JPA Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // 이메일 중복 여부 확인
    boolean existsByEmail(String email);
}
