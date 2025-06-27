package com.example.milo_be.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 역할극 캐릭터 생성/수정 요청 DTO
 */
@Getter
@Setter
public class RoleCharacterDto {
    private String userId;
    private String name;
    private String relationship;
    private String tone;
    private String personality;
    private String situation;
}
