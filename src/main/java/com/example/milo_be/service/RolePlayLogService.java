package com.example.milo_be.service;

import com.example.milo_be.domain.entity.RolePlayLog;
import com.example.milo_be.dto.RolePlayLogDto;
import com.example.milo_be.repository.RolePlayLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolePlayLogService {

    private final RolePlayLogRepository rolePlayLogRepository;

    public List<RolePlayLogDto> fetchLogs(String userId) {
        List<RolePlayLog> logs = rolePlayLogRepository.findByUser_UserIdOrderByCreatedAtAsc(userId);

        return logs.stream()
                .map(log -> new RolePlayLogDto(
                        log.getSender(),
                        log.getResponder(),
                        log.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ))
                .collect(Collectors.toList());
    }
}
