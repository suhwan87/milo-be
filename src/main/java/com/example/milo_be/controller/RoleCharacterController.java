package com.example.milo_be.controller;

import com.example.milo_be.dto.RoleCharacterDto;
import com.example.milo_be.service.RoleCharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/character")
@RequiredArgsConstructor
public class RoleCharacterController {

    private final RoleCharacterService roleCharacterService;

    @PostMapping
    public ResponseEntity<Long> saveCharacter(@RequestBody RoleCharacterDto dto) {
        Long characterId = roleCharacterService.save(dto);
        return ResponseEntity.ok(characterId);
    }
}
