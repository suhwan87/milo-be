package com.example.milo_be.controller;

import com.example.milo_be.JWT.JwtUtil;
import com.example.milo_be.dto.LoginRequestDto;
import com.example.milo_be.dto.UserRequestDto;
import com.example.milo_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

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
     * 🔓 로그인 요청
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginDto) {
        String token = userService.login(loginDto.getUserId(), loginDto.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    /**
     * 🗑️ 회원탈퇴 요청 (비밀번호 확인 포함)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> requestBody
    ) {
        try {
            // 1. JWT에서 userId 추출
            String jwt = token.startsWith("Bearer ") ? token.substring(7).trim() : token;
            String userId = jwtUtil.getUserIdFromToken(jwt);

            // 2. 요청 본문에서 비밀번호 추출
            String password = requestBody.get("password");

            // 3. 비밀번호 검증 후 삭제
            boolean deleted = userService.deleteUserWithPassword(userId, password);
            if (deleted) {
                return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("비밀번호가 일치하지 않습니다.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("회원탈퇴 실패: " + e.getMessage());
        }
    }

}
