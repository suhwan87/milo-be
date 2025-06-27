package com.example.milo_be.dto;

import lombok.Getter;
import lombok.Setter;

public class FindUserDto {
    @Getter
    @Setter
    public static class FindIdRequestDto {
        private String nickname;
        private String email;
    }

    @Getter
    @Setter
    public static class FindPasswordRequestDto {
        private String nickname;
        private String userId;
        private String email;
    }
}
