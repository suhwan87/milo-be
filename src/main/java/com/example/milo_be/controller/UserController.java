package com.example.milo_be.controller;

import com.example.milo_be.dto.UserRequestDto;
import com.example.milo_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequestDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}

