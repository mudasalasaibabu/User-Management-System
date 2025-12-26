package com.example.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.usermanagement.entity.ForgotPassword;
import com.example.usermanagement.entity.User;
@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer>{
	@Query( value = "SELECT * FROM forgot_password WHERE otp = ?1 AND user_id = ?2", nativeQuery = true)
	Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

	Optional<ForgotPassword> findByUser(User user);

}

