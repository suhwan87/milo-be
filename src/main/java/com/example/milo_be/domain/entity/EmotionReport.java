package com.example.milo_be.domain.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emotion_report_TB")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmotionReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "MAIN_EMOTION", nullable = false, length = 20)
    private String mainEmotion;

    @Column(name = "SUMMARY", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(name = "FEEDBACK", nullable = false, columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "ENCOURAGEMENT", nullable = false, columnDefinition = "TEXT")
    private String encouragement;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
}

