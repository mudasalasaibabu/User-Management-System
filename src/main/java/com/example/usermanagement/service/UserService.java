package com.example.usermanagement.service;

import java.util.List;

import com.example.usermanagement.dto.ChangePasswordRequestDTO;
import com.example.usermanagement.dto.RegisterUserRequestDTO;
import com.example.usermanagement.dto.RegisterUserResponseDTO;
import com.example.usermanagement.dto.UpdateUserRequestDTO;

public interface UserService {
	RegisterUserResponseDTO registerUser(RegisterUserRequestDTO dto);
	RegisterUserResponseDTO getUserByEmail(String emailId);
	RegisterUserResponseDTO getUserById(Long id);
	List<RegisterUserResponseDTO> getAllUsers();
	RegisterUserResponseDTO updateUser(Long id, UpdateUserRequestDTO dto);
	void deleteUser(Long id);
	public RegisterUserResponseDTO getMyProfile();
	public void changePassword(ChangePasswordRequestDTO dto);
	public List<RegisterUserResponseDTO> getActiveUsersToday();
	void toggleUserStatus(Long userId);


}
