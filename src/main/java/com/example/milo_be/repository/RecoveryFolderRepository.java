package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.RecoveryFolder;
import com.example.milo_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * RecoveryFolder 엔터티 전용 JPA Repository
 */
public interface RecoveryFolderRepository extends JpaRepository<RecoveryFolder, Long> {
    // 동일 사용자 내 폴더명이 중복되는지 여부 확인
    boolean existsByUserAndFolderName(User user, String folderName);

    // 특정 사용자의 모든 회복 폴더 조회
    List<RecoveryFolder> findAllByUser(User user);

    // 폴더 ID와 사용자 정보로 특정 폴더 조회
    Optional<RecoveryFolder> findByFolderIdAndUser(Long folderId, User user);
}

