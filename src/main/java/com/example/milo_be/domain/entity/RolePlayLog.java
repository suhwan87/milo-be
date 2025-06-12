package com.example.milo_be.domain.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "role_play_log_TB")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePlayLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_CHAT_ID")
    private Long roleChatId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "SENDER", nullable = false, columnDefinition = "TEXT")
    private String sender;

    @Column(name = "RESPONDER", nullable = false, columnDefinition = "TEXT")
    private String responder;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
}

