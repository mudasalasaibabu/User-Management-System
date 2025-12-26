package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.DashboardResponseDTO;
import com.example.usermanagement.dto.MonthlyRegistrationDTO;
import com.example.usermanagement.dto.WeeklyActivityDTO;
import com.example.usermanagement.serviceimplementation.DashboardService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDTO> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }
    @GetMapping("/registrations/monthly")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MonthlyRegistrationDTO>> getMonthlyRegistrations() {
        return ResponseEntity.ok(dashboardService.getMonthlyRegistrations());
    }
    
    @GetMapping("/activity/weekly")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WeeklyActivityDTO>> getWeeklyActivity() {
        return ResponseEntity.ok(dashboardService.getWeeklyActivity());
    }

}

