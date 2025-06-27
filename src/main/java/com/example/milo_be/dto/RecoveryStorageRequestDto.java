package com.example.milo_be.dto;

import lombok.*;

/**
 * 회복 문장 및 폴더 관련 요청 DTO 모음
 */
public class RecoveryStorageRequestDto {
    // 폴더 생성 요청 DTO
    @Getter
    @Setter
    public static class RecoveryFolderCreateRequest {
        private String folderName;
    }

    // 폴더명 수정 요청 DTO
    @Getter
    @Setter
    public static class RecoveryFolderUpdateRequest {
        private Long folderId;
        private String updatedName;
    }

    // 폴더 삭제 요청 DTO
    @Getter
    @Setter
    public static class RecoveryFolderDeleteRequest {
        private Long folderId;
        private String content;
    }

    // 회복 문장 저장 요청 DTO
    @Getter
    @Setter
    public static class RecoverySentenceRequest {
        private Long folderId;
        private String content;
    }

    // 회복 문장 삭제 요청 DTO
    @Getter
    @Setter
    public static class FolderSentenceDeleteRequest {
        private Long folderId;
        private Long sentenceId;
    }

    // 회복 문장 수정 요청 DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FolderSentenceUpdateRequest {
        private Long sentenceId;
        private String updatedContent;
    }
}
