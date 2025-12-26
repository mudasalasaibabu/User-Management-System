package com.example.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.entity.SystemSettings;
import com.example.usermanagement.serviceimplementation.SystemSettingsService;

@RestController
@RequestMapping("/api/admin/system-settings")
@PreAuthorize("hasRole('ADMIN')")
public class SystemSettingsController {

    @Autowired
    private SystemSettingsService service;

    @GetMapping
    public ResponseEntity<SystemSettings> getSettings() {
        return ResponseEntity.ok(service.getSettings());
    }

    @PutMapping
    public ResponseEntity<SystemSettings> updateSettings(
            @RequestBody SystemSettings settings) {
        return ResponseEntity.ok(service.updateSettings(settings));
    }
}

