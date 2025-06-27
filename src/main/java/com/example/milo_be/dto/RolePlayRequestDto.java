package com.example.milo_be.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 역할극 대화 요청 DTO (→ FastAPI)
 */
@Getter
@Setter
public class RolePlayRequestDto {
    private String user_id;        // FastAPI에서 기대하는 JSON 형식
    private Long character_id;
    private String input;
}