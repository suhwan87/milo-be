// src/main/java/com/example/milo_be/dto/LoginRequestDto.java
package com.example.milo_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
    private String userId;
    private String password;
}