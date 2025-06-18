package com.example.milo_be.controller;

import com.example.milo_be.dto.LoginRequestDto;
import com.example.milo_be.dto.UserRequestDto;
import com.example.milo_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 🔐 회원가입 요청
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequestDto dto) {
        userService.registerUser(dto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /**
     * ✅ 아이디 중복 확인 요청
     * 프론트: /api/users/check-id?id=test123
     */
    @GetMapping("/check-id")
    public ResponseEntity<Boolean> checkUserId(@RequestParam String id) {
        boolean isAvailable = userService.isUserIdAvailable(id);
        return ResponseEntity.ok(isAvailable);
    }

    /**
     *  로그인 요청
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginDto) {
        String token = userService.login(loginDto.getUserId(), loginDto.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

}
