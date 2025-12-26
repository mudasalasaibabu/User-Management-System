package com.example.usermanagement.dto;

public class ChangePasswordForForgotPurpose {
	private String password;
    private String repeatPassword;

    public ChangePasswordForForgotPurpose() {
    }

    public ChangePasswordForForgotPurpose(String password, String repeatPassword) {
		super();
		this.password = password;
		this.repeatPassword = repeatPassword;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
