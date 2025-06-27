package com.example.milo_be.dto;

import java.time.LocalDate;

/**
 * 하루 감정 요약 DTO
 * - 특정 날짜의 주요 감정 정보를 담음
 */
public record DayEmotionDto(LocalDate date, String mainEmotion) { }