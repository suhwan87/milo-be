package com.example.milo_be.controller;

import com.example.milo_be.dto.RolePlayRequestDto;
import com.example.milo_be.service.FastApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/roleplay")
@RequiredArgsConstructor
public class RolePlayController {

    private final FastApiService fastApiService;

    @PostMapping
    public ResponseEntity<Map<String, String>> rolePlay(@RequestBody RolePlayRequestDto request) {
        String output = fastApiService.sendChatToFastAPI(request.getUser_id(), request.getCharacter_id(), request.getInput());
        return ResponseEntity.ok(Map.of("output", output));
    }
}
