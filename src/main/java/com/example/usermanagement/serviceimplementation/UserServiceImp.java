package com.example.usermanagement.serviceimplementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.ChangePasswordRequestDTO;
import com.example.usermanagement.dto.RegisterUserRequestDTO;
import com.example.usermanagement.dto.RegisterUserResponseDTO;
import com.example.usermanagement.dto.UpdateUserRequestDTO;
import com.example.usermanagement.entity.Role;
import com.example.usermanagement.entity.SystemSettings;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.exceptionHandler.AccessDeniedException;
import com.example.usermanagement.exceptionHandler.EmailAlreadyExistsException;
import com.example.usermanagement.exceptionHandler.InvalidCredentialsException;
import com.example.usermanagement.exceptionHandler.UserNotFoundException;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.UserService;
@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SystemSettingsService systemSettingsService;
	@Override
	public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO dto) {

	    if (userRepo.existsByEmailId(dto.getEmailId())) {
	        throw new EmailAlreadyExistsException("Email already exists");
	    }
	    SystemSettings settings = systemSettingsService.getSettings();
	    if (!settings.isRegistrationEnabled()) {
	        throw new RuntimeException("User registration is currently disabled");
	    }
	    long totalUsers = userRepo.count();
	    if (totalUsers >= settings.getMaxUsers()) {
	        throw new RuntimeException("Maximum user limit reached");
	    }
	    User user = new User();
	    user.setUserName(dto.getUserName());
	    user.setEmailId(dto.getEmailId());
	    user.setPassword(passwordEncoder.encode(dto.getPassword()));
	    user.setRole(Role.USER);
	    user.setEnabled(true);
	    User savedUser = userRepo.save(user);
	    return mapToResponseDTO(savedUser);
	}


	private RegisterUserResponseDTO mapToResponseDTO(User user) {
	    RegisterUserResponseDTO dto = new RegisterUserResponseDTO();
	    dto.setId(user.getId());
	    dto.setUserName(user.getUserName());
	    dto.setEmailId(user.getEmailId());
	    dto.setRole(user.getRole());

	    dto.setEnabled(user.isEnabled()); //THIS LINE FIXES EVERYTHING

	    dto.setCreatedAt(user.getCreatedAt());
	    dto.setUpdatedAt(user.getUpdatedAt());
	    dto.setLastLoginAt(user.getLastLoginAt());

	    return dto;
	}


	@Override
	public RegisterUserResponseDTO getUserByEmail(String emailId) {
		User user = userRepo.findByEmailId(emailId).orElseThrow(()->new UserNotFoundException("User Not Found"));
		String loogedEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
		.getAuthorities().stream()
		.anyMatch(auth->auth.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin && !user.getEmailId().equals(loogedEmail)) {
			 throw new AccessDeniedException(
					 "You are not authorized to view this user"
			        );
		}
		return mapToResponseDTO(user);
	}

	@Override
	public RegisterUserResponseDTO getUserById(Long id) {
		User user = userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User Not Found"));
		String loogedEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
		.getAuthorities().stream()
		.anyMatch(auth->auth.getAuthority().equals("ROLE_ADMIN"));
		
		if(!isAdmin && !user.getEmailId().equals(loogedEmail)) {
			 throw new AccessDeniedException(
			            "You are not authorized to view this user"
			        );
		}
		return mapToResponseDTO(user);
	}

	@Override
	public List<RegisterUserResponseDTO> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<RegisterUserResponseDTO> dto=new ArrayList<>();
		for(User user:users) {
			dto.add(mapToResponseDTO(user));
		}
		return dto;
	}
	@Override
	public RegisterUserResponseDTO updateUser(Long id, UpdateUserRequestDTO dto) {

	    User user = userRepo.findById(id)
	        .orElseThrow(() -> new UserNotFoundException("User Not Found"));

	    String loggedEmail = SecurityContextHolder
	        .getContext()
	        .getAuthentication()
	        .getName();

	    boolean isAdmin = SecurityContextHolder
	        .getContext()
	        .getAuthentication()
	        .getAuthorities()
	        .stream()
	        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

	    //RULE 1: DISABLED USER CANNOT BE EDITED
	    if (!user.isEnabled()) {
	        throw new AccessDeniedException("Disabled user cannot be updated");
	    }

	    //RULE 2: USER CAN EDIT ONLY SELF
	    if (!isAdmin && !user.getEmailId().equals(loggedEmail)) {
	        throw new AccessDeniedException("You can update only your own profile");
	    }

	   //RULE 3: ADMIN CANNOT EDIT ANOTHER ADMIN 
	    if (isAdmin && user.getRole() == Role.ADMIN && !user.getEmailId().equals(loggedEmail)) {
	        throw new AccessDeniedException("Admin cannot edit another admin profile");
	    }

	    // UPDATE ALLOWED FIELDS 
	    user.setUserName(dto.getUserName());

	    if (isAdmin && dto.getEmailId() != null
	            && !dto.getEmailId().equals(user.getEmailId())) {

	        if (userRepo.existsByEmailId(dto.getEmailId())) {
	            throw new EmailAlreadyExistsException("Email already exists");
	        }
	        user.setEmailId(dto.getEmailId());
	    }

	    User updatedUser = userRepo.save(user);
	    return mapToResponseDTO(updatedUser);
	}


	@Override
	public void deleteUser(Long id) {
		User user = userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User Not Found"));
		userRepo.delete(user);
	}

	@Override
	public RegisterUserResponseDTO getMyProfile() {
		String loggedEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByEmailId(loggedEmail ).orElseThrow(()->new UserNotFoundException("User Not Found"));
		return mapToResponseDTO(user);
	}

	@Override
	public void changePassword(ChangePasswordRequestDTO dto) {

		    String email = SecurityContextHolder.getContext()
		                    .getAuthentication()
		                    .getName();

		    User user = userRepo.findByEmailId(email)
		        .orElseThrow(() -> new UserNotFoundException("User Not Found"));
		    if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
		        throw new InvalidCredentialsException("Old password is incorrect");
		    }
		    if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())){
		        throw new InvalidCredentialsException(
		            "New password cannot be same as old password"
		        		);
		    }

		    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		    userRepo.save(user);
		}
	
	public List<RegisterUserResponseDTO> getActiveUsersToday() {

	    LocalDateTime startOfDay = LocalDate.now().atStartOfDay();

	    List<User> activeUsers = userRepo.findActiveUsersToday(startOfDay);

	    List<RegisterUserResponseDTO> responseList = new ArrayList<>();

	    for (User user : activeUsers) {
	    	RegisterUserResponseDTO dto = mapToResponseDTO(user);
	        responseList.add(dto);
	    }

	    return responseList;
	}


	@Override
	public void toggleUserStatus(Long userId) {

	    User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
	    if (user.getRole() == Role.ADMIN) {
	        throw new IllegalStateException("Admin users cannot be disabled");
	    }
	    user.setEnabled(!user.isEnabled());
	    userRepo.save(user);
	}




}
