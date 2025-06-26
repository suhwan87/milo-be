package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.RecoveryFolder;
import com.example.milo_be.domain.entity.RecoverySentence;
import com.example.milo_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecoverySentenceRepository extends JpaRepository<RecoverySentence, Long> {
    List<RecoverySentence> findAllByFolderAndUser(RecoveryFolder folder, User user);

    // 회복 문장 전체 삭제
    void deleteAllByFolder(RecoveryFolder folder);

    List<RecoverySentence> findAllByUserAndContent(User user, String content);

    int countByFolder(RecoveryFolder folder);

    // 사용자 ID에 해당하는 모든 데이터를 삭제
    void deleteByUser_UserId(String userId);
}
