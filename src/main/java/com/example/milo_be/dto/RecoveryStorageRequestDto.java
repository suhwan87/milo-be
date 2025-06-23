package com.example.milo_be.dto;

import lombok.*;

public class RecoveryStorageRequestDto {

    @Getter
    @Setter
    public static class RecoveryFolderCreateRequest {
        private String folderName;
    }

    @Getter
    @Setter
    public static class RecoveryFolderDeleteRequest {
        private Long folderId;
        private String content;
    }

    @Getter
    @Setter
    public static class RecoverySentenceRequest {
        private Long folderId;
        private String content;
    }

    @Getter
    @Setter
    public static class FolderSentenceDeleteRequest {
        private Long folderId;
        private Long sentenceId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FolderSentenceUpdateRequest {
        private Long sentenceId;
        private String updatedContent;
    }
}
