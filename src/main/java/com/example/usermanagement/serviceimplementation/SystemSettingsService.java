package com.example.usermanagement.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usermanagement.entity.SystemSettings;
import com.example.usermanagement.repository.SystemSettingsRepository;

import jakarta.transaction.Transactional;

@Service
public class SystemSettingsService {

    @Autowired
    private SystemSettingsRepository repo;
    @Transactional
    public SystemSettings getSettings() {
        return repo.findTopByOrderByIdAsc()
                .orElseGet(() -> repo.save(new SystemSettings()));
    }
    @Transactional
    public SystemSettings updateSettings(SystemSettings settings) {
        settings.setId(getSettings().getId());
        return repo.save(settings);
    }
}


