package com.example.usermanagement.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.AuthResponseDTO;
import com.example.usermanagement.dto.LoginRequestDTO;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.exceptionHandler.InvalidCredentialsException;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.security.JwtTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtTokenService jwtService;
	@Autowired
	private UserRepository userRepo;
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login( @Valid @RequestBody LoginRequestDTO dto) {
	    Authentication authentication = authManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    dto.getEmailId(),
	                    dto.getPassword()
	            )
	    );

	    String email = authentication.getName();
	    User user = userRepo.findByEmailId(email).orElseThrow(() ->new InvalidCredentialsException("Invalid email or password"));
	    if (!user.isEnabled()) {
	        throw new DisabledException("Account is disabled. Contact admin.");
	    }
	    user.setLastLoginAt(LocalDateTime.now());
	    userRepo.save(user);
	    String jwt = jwtService.generateToken(email);
	    ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
	            .httpOnly(true)
	            .secure(false) 
	            .path("/")
	            .sameSite("Lax")
	            .maxAge(7 * 24 * 60 * 60)
	            .build();
	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, cookie.toString())
	            .body(new AuthResponseDTO("Login successful"));
	}


	@PostMapping("/logout")
	public ResponseEntity<AuthResponseDTO> logout(HttpServletResponse response) {

	    Cookie cookie = new Cookie("jwt", null);
	    cookie.setHttpOnly(true);
	    cookie.setSecure(false);
	    cookie.setPath("/");
	    cookie.setMaxAge(0);

	    response.addCookie(cookie);

	    return ResponseEntity.ok(
	        new AuthResponseDTO("Logged out successfully")
	    );
	}



}
