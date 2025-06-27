package com.example.milo_be.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 회복 문장 및 폴더 응답 DTO 모음
 */
public class RecoveryStorageResponseDto {
    // 회복 폴더 응답 DTO
    @Getter
    @Setter
    @Builder
    public static class RecoveryFolderResponse {
        private Long folderId;
        private String folderName;
        private LocalDateTime createdAt;
        private int sentenceCount;  // 포함된 문장 개수
    }

    // 회복 문장 응답 DTO
    @Getter
    @Setter
    @Builder
    public static class RecoverySentenceResponse {
        private Long sentenceId;
        private String content;
        private Long folderId;
        private LocalDateTime createdAt;
    }
}
