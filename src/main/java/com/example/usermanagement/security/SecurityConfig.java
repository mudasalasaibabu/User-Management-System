package com.example.usermanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;
	  @Autowired
	    private PasswordEncoder passwordEncoder;
	  @Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(customizer->customizer.disable());
		http.cors(cors -> {});
		http.authorizeHttpRequests(authorizeHttp->authorizeHttp
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers("/api/users/register","/api/auth/login").permitAll().anyRequest().authenticated());
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	/*Mana simple Telugu language lo cheptha 
	1. AuthenticationProvider ante -> nijanga user correct aa kaada ani check chese vadu (username DB lo unda, password match ayinda)
	2. AuthenticationManager ante -> ee check ni start chesi, evaritho check cheyinchalo decide chese vadu
	Short ga cheppalante 
	1. AuthenticationProvider pani chestundi
 	2. AuthenticationManager pani ni manage chestundi
	Inka simple example 
	AuthenticationManager:
	1. “Ee login ni check cheyyandi” ani order isthadu
	AuthenticationProvider:
	2. “Ok, nenu DB check chesi cheptha correct aa kaada” ani work chestadu
	One-line Telugu version 
	AuthenticationProvider user ni nijanga authenticate chestundi, AuthenticationManager aa process ni control & manage chestundi.*/
	@Bean
	public AuthenticationProvider authProvider() {
		  DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		  daoProvider.setUserDetailsService(userDetailsService);
		  daoProvider.setPasswordEncoder(passwordEncoder);
		  return  daoProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
