package com.example.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.entity.SystemSettings;
@Repository
public interface SystemSettingsRepository extends JpaRepository<SystemSettings, Long> {
Optional<SystemSettings> findTopByOrderByIdAsc();
}

