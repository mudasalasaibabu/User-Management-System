package com.example.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usermanagement.dto.AuthResponseDTO;
import com.example.usermanagement.dto.ChangePasswordRequestDTO;
import com.example.usermanagement.dto.RegisterUserRequestDTO;
import com.example.usermanagement.dto.RegisterUserResponseDTO;
import com.example.usermanagement.dto.UpdateUserRequestDTO;
import com.example.usermanagement.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<RegisterUserResponseDTO> register(@Valid @RequestBody RegisterUserRequestDTO dto){
		RegisterUserResponseDTO response = userService.registerUser(dto);
		return new ResponseEntity<RegisterUserResponseDTO>(response,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<RegisterUserResponseDTO> getUserById(@PathVariable Long id){
		RegisterUserResponseDTO response = userService.getUserById(id);
		return new ResponseEntity<RegisterUserResponseDTO>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/email/{email}")
	public ResponseEntity<RegisterUserResponseDTO> getUserByEmail(@PathVariable String email){
		RegisterUserResponseDTO response = userService.getUserByEmail(email);
		return new ResponseEntity<RegisterUserResponseDTO>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<RegisterUserResponseDTO>> getAllUsers(){
		List<RegisterUserResponseDTO> allUsers = userService.getAllUsers();
		return new ResponseEntity<List<RegisterUserResponseDTO>>(allUsers,HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<RegisterUserResponseDTO> updateUser(@PathVariable Long id,@Valid @RequestBody UpdateUserRequestDTO dto){
		RegisterUserResponseDTO updateUser = userService.updateUser(id, dto);
		return new ResponseEntity<RegisterUserResponseDTO>(updateUser,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return new ResponseEntity<String>("User Deleted Successfully",HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@PutMapping("/change-password")
	public ResponseEntity<AuthResponseDTO> changePassword(
	        @Valid @RequestBody ChangePasswordRequestDTO dto) {

	    userService.changePassword(dto);

	    return ResponseEntity.ok(
	        new AuthResponseDTO("Password changed successfully")
	    );
	}

	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/me")
	public ResponseEntity<RegisterUserResponseDTO> getMyProfile() {
	    RegisterUserResponseDTO response = userService.getMyProfile();
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/admin/users/{id}/toggle-status")
	public ResponseEntity<AuthResponseDTO> toggleUserStatus(@PathVariable Long id) {

	    userService.toggleUserStatus(id);

	    return ResponseEntity.ok(
	        new AuthResponseDTO("User status updated successfully")
	    );
	}

}
