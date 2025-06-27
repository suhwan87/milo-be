package com.example.milo_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 역할극 대화 로그 응답 DTO
 */
@Getter
@Setter
@AllArgsConstructor
public class RolePlayLogDto {
    private String sender;
    private String responder;
    private String createdAt;
}
