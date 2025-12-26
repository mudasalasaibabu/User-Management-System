package com.example.usermanagement.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="forgotpassword")
public class ForgotPassword {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fp_id")
	private Integer fpId;
	@Column(name="otp",nullable=false)
	private Integer otp;
	@Column(name="expiration_time",nullable=false)
	private Date expirationTime;
	@OneToOne
	private User user;
	public ForgotPassword() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ForgotPassword(Integer otp, Date expirationTime, User user) {
		super();
		this.otp = otp;
		this.expirationTime = expirationTime;
		this.user = user;
	}
	public Integer getFpId() {
		return fpId;
	}
	public void setFpId(Integer fpId) {
		this.fpId = fpId;
	}
	public Integer getOtp() {
		return otp;
	}
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	public Date getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
