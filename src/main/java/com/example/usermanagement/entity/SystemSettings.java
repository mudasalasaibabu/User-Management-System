package com.example.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "system_settings")
public class SystemSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registration_enabled")
    private boolean registrationEnabled = true;

    @Column(name = "maintenance_mode")
    private boolean maintenanceMode = false;

    @Column(name = "max_users")
    private int maxUsers = 6000;

    @Column(name = "password_policy")
    private String passwordPolicy = "STRONG";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isRegistrationEnabled() {
		return registrationEnabled;
	}

	public void setRegistrationEnabled(boolean registrationEnabled) {
		this.registrationEnabled = registrationEnabled;
	}

	public boolean isMaintenanceMode() {
		return maintenanceMode;
	}

	public void setMaintenanceMode(boolean maintenanceMode) {
		this.maintenanceMode = maintenanceMode;
	}

	public int getMaxUsers() {
		return maxUsers;
	}

	public void setMaxUsers(int maxUsers) {
		this.maxUsers = maxUsers;
	}

	public String getPasswordPolicy() {
		return passwordPolicy;
	}

	public void setPasswordPolicy(String passwordPolicy) {
		this.passwordPolicy = passwordPolicy;
	}
    
}

