package com.example.milo_be.domain.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emotion_vector_TB")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmotionVector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMOTION_ID")
    private Long emotionId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "MAIN_EMOTION", nullable = false, length = 20)
    private String mainEmotion;

    @Column(name = "SCORE", nullable = false)
    private float score;

    @Column(name = "JOY", nullable = false)
    private float joy;

    @Column(name = "SADNESS", nullable = false)
    private float sadness;

    @Column(name = "ANGER", nullable = false)
    private float anger;

    @Column(name = "EMBARRASSMENT", nullable = false)
    private float embarrassment;

    @Column(name = "HURT", nullable = false)
    private float hurt;

    @Column(name = "ANXIETY", nullable = false)
    private float anxiety;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
}

