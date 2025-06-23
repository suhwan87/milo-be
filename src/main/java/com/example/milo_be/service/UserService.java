package com.example.milo_be.service;

import com.example.milo_be.JWT.JwtUtil;
import com.example.milo_be.domain.entity.User;
import com.example.milo_be.dto.UserRequestDto;
import com.example.milo_be.dto.UserResponseDto;
import com.example.milo_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 회원가입 처리
     */
    public void registerUser(UserRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .emotionPrompt(dto.getEmotionPrompt() != null ? dto.getEmotionPrompt() : 0)
                .email(dto.getEmail())
                .build();

        userRepository.save(user);
    }

    /**
     * 아이디 중복 체크
     */
    public boolean isUserIdAvailable(String userId) {
        return !userRepository.existsById(userId);
    }

    /**
     * 로그인 처리 및 JWT 발급
     */
    public String login(String userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(userId);
    }

    /**
     * 회원탈퇴 (비밀번호 확인 포함)
     */
    public boolean deleteUserWithPassword(String userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return false;
        }

        userRepository.delete(user);
        return true;
    }

    /**
     * 닉네임 변경
     */
    public void updateNickname(String userId, String newNickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        user.setNickname(newNickname);
        userRepository.save(user);
    }

    /**
     * 현재 로그인한 사용자 정보 반환 (GET /api/users/me)
     */
    public UserResponseDto getUserInfo(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        return new UserResponseDto(user.getUserId(), user.getNickname(), user.getEmail());
    }

    /**
     * 회원 비밀번호 변경
     */
    public void changePassword(String userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * 회원 챗봇 공감형/조언형
     */
    public String getPromptType(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));
        return user.getEmotionPrompt() == 0 ? "emotional" : "practical";
    }

    public void updatePrompt(String userId, int promptValue) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다: " + userId));

        user.setEmotionPrompt(promptValue);
        userRepository.save(user);
    }
}
