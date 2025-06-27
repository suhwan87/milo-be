package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.RecoveryFolder;
import com.example.milo_be.domain.entity.RecoverySentence;
import com.example.milo_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * RecoverySentence 엔터티 전용 JPA Repository
 */
public interface RecoverySentenceRepository extends JpaRepository<RecoverySentence, Long> {
    // 특정 폴더 + 사용자 기준 회복 문장 전체 조회
    List<RecoverySentence> findAllByFolderAndUser(RecoveryFolder folder, User user);

    // 특정 폴더의 회복 문장 전체 삭제
    void deleteAllByFolder(RecoveryFolder folder);

    // 사용자 + 문장 내용으로 회복 문장 검색
    List<RecoverySentence> findAllByUserAndContent(User user, String content);

    // 특정 폴더에 포함된 회복 문장 개수 조회
    int countByFolder(RecoveryFolder folder);
}
