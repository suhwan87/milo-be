package com.example.milo_be.repository;

import com.example.milo_be.domain.entity.RoleCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleCharacterRepository extends JpaRepository<RoleCharacter, Long> {
}
