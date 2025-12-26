package com.example.usermanagement.dto;
public class DashboardResponseDTO {

    private long totalUsers;
    private long newThisMonth;
    private long adminUsers;
    private long activeToday;

    public DashboardResponseDTO(long totalUsers,long newThisMonth,long adminUsers,long activeToday) {
        this.totalUsers = totalUsers;
        this.newThisMonth = newThisMonth;
        this.adminUsers = adminUsers;
        this.activeToday = activeToday;
    }

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getNewThisMonth() {
		return newThisMonth;
	}

	public void setNewThisMonth(long newThisMonth) {
		this.newThisMonth = newThisMonth;
	}

	public long getAdminUsers() {
		return adminUsers;
	}

	public void setAdminUsers(long adminUsers) {
		this.adminUsers = adminUsers;
	}

	public long getActiveToday() {
		return activeToday;
	}

	public void setActiveToday(long activeToday) {
		this.activeToday = activeToday;
	}
    
}
