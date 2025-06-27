package com.example.milo_be.service;

import com.example.milo_be.JWT.JwtUtil;
import com.example.milo_be.domain.entity.User;
import com.example.milo_be.dto.*;
import com.example.milo_be.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

/**
 * 사용자 회원 관리 서비스
 * - 회원가입, 로그인, 탈퇴, 비밀번호 변경, 리포트 상태 확인
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RecoverySentenceRepository recoverySentenceRepository;
    private final RecoveryFolderRepository recoveryFolderRepository;
    private final EmotionReportRepository reportRepository;
    private final MonthlyEmotionSummaryRepository monthlyEmotionSummaryRepository;
    private final RoleCharacterRepository roleCharacterRepository;
    private final RolePlayLogRepository rolePlayLogRepository;
    private final ChatLogRepository chatLogRepository;


    // 회원가입 처리
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

    // 아이디 중복 체크
    public boolean isUserIdAvailable(String userId) {
        return !userRepository.existsById(userId);
    }

    // 로그인 처리 및 JWT 발급
    public LoginResponseDto login(String userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.generateToken(userId);
        return new LoginResponseDto(token, userId);
    }

    // 회원탈퇴 (비밀번호 확인 포함)
    public boolean deleteUserWithPassword(String userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return false;
        }

        userRepository.delete(user);
        return true;
    }

    // 닉네임 변경
    public void updateNickname(String userId, String newNickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        user.setNickname(newNickname);
        userRepository.save(user);
    }

    // 현재 로그인한 사용자 정보 반환
    public UserResponseDto getUserInfo(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        return new UserResponseDto(user.getUserId(), user.getNickname(), user.getEmail());
    }

    // 회원 비밀번호 변경
    public void changePassword(String userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    // 사용자 리포트 상태 확인 (신규가입자, 리포트 경험, 오늘 리포트 여부)
    public UserReportStatusDto getUserReportStatus(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        LocalDate today = LocalDate.now();
        boolean isNewUser = user.getCreatedAt().toLocalDate().isEqual(today);
        boolean hasAnyReport = reportRepository.existsByUser(user);
        boolean hasTodayReport = reportRepository.existsByUserAndDate(user, today);

        return new UserReportStatusDto(isNewUser, hasAnyReport, hasTodayReport);
    }


    // 유저 앱 초기화
    public void resetUserData(String userId) {
        recoverySentenceRepository.deleteByUser_UserId(userId);
        recoveryFolderRepository.deleteByUser_UserId(userId);
        reportRepository.deleteByUser_UserId(userId);
        monthlyEmotionSummaryRepository.deleteByUser_UserId(userId);
        rolePlayLogRepository.deleteByUser_UserId(userId);
        roleCharacterRepository.deleteByUser_UserId(userId);
        chatLogRepository.deleteByUser_UserId(userId);
    }

    // 아이디 찾기
    public String findUserId(FindUserDto.FindIdRequestDto request) {
        return userRepository.findByNicknameAndEmail(request.getNickname(), request.getEmail())
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException("일치하는 사용자를 찾을 수 없습니다."));
    }

    // 비밀번호 찾기
    public String generateAndApplyTempPassword(FindUserDto.FindPasswordRequestDto dto) {
        Optional<User> optionalUser = userRepository.findByNicknameAndUserIdAndEmail(
                dto.getNickname(), dto.getUserId(), dto.getEmail()
        );
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("일치하는 사용자가 없습니다.");
        }
        User user = optionalUser.get();
        String tempPassword = generateRandomPassword(); // 8자리 랜덤 비밀번호
        String encodedPassword = passwordEncoder.encode(tempPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return tempPassword;
    }

    // 랜덤 비밀번호 생성 함수
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
