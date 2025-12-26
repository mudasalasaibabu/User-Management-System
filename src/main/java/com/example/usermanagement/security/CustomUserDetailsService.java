package com.example.usermanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.usermanagement.entity.User;
import com.example.usermanagement.exceptionHandler.UserNotFoundException;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.serviceimplementation.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmailId(username).orElseThrow(()->new UserNotFoundException("User Not Found"));
		return new UserPrincipal(user);
	}

}
