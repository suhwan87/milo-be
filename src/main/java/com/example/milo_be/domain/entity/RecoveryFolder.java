package com.example.milo_be.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * recovery_folder_TB 테이블 매핑 엔터티
 * 사용자별 회복 문장 폴더 정보 저장
 */
@Entity
@Table(name = "recovery_folder_TB",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "FOLDER_NAME"})})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryFolder {

    // PK: 폴더 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOLDER_ID")
    private Long folderId;

    // FK: 사용자 ID
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // 폴더명 (사용자별 고유)
    @Setter
    @Column(name = "FOLDER_NAME", nullable = false, length = 100)
    private String folderName;

    // 생성 시간
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
