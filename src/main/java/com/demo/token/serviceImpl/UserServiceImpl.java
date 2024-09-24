package com.demo.token.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.demo.token.dto.AuthenticationResponse;
import com.demo.token.dto.UsersDTO;
import com.demo.token.exception.ResourceNotFoundException;
import com.demo.token.model.Users;
import com.demo.token.model.Users.Role;
import com.demo.token.repo.UserRepository;
import com.demo.token.service.UsersService;
import com.demo.token.serviceutility.UtilService;
import com.demo.token.validation.EmailValidator;
import com.demo.token.validation.PhoneNumberValidation;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UsersService {
	/**
	 * Repository for performing CRUD operations on Users table. Used to interact
	 * with the database for managing users-related data.
	 */
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UtilService utilService;

	public UsersDTO convertToDTO(Users users) {
		UsersDTO usersDTO = utilService.getModelMapper().map(users, UsersDTO.class);
		usersDTO.setMessage("User Sucessfully Registered ");
		return usersDTO;

//		return new UsersDTO(users.getUuid(), users.getName(), users.getEmail(), users.getUserName(), users.getRole(),
//				users.getIsActive(),
//				"User Sucessfully Registered , Please check your mail for further access's credentials ");
//		
	}

	@Override
	@Transactional
	public UsersDTO createUser(Users users) {
		if (!PhoneNumberValidation.isValid(users.getPhoneNumber())) {
			throw new IllegalArgumentException("Invalid phone number format.");
		}
		if (!EmailValidator.isValid(users.getEmail())) {
			throw new IllegalArgumentException("Invalid email, Please check your email.");
		}
		Optional<Users> existingUser = userRepository.findByuserNameAndIsActive(users.getUserName(), true);
		if (existingUser.isPresent()) {
			throw new IllegalStateException("A user with this userName already exists and is active.");
		}

		Optional<Users> existingPhone = userRepository.findByPhoneNumberAndIsActive(users.getPhoneNumber(), true);
		if (existingPhone.isPresent()) {
			throw new IllegalStateException("Phone number is already exist.");
		}

		if (users.getRole().equals(Role.TRAINEE) || users.getRole().equals(Role.ATTENDEE)) {
			utilService.getMailService().sendEmail(users.getEmail(), users.getName(), users.getEmail(),
					users.getPhoneNumber(), users.getUserName(), users.getPassword(), users.getRole());
			users.setIsActive(true);
			users.setPassword(utilService.getPasswordEncoder().encode(users.getPassword()));

			Users savedUsers = userRepository.save(users);
			UsersDTO usersDTO = convertToDTO(savedUsers);
			usersDTO.setMessage("User Sucessfully Register as " + "" + users.getRole() + ""
					+ " please check your mail for login credentials");
			return usersDTO;
		} else {
			throw new IllegalArgumentException("Please check the role is either (TRAINEE or ATTENDEE)");

		}
	}

	@Override
	public AuthenticationResponse authenticate(Users request) {
//		if (request.getUserName() == null && request.getPassword() == null) {
//			throw new IllegalArgumentException("UserName and Password should not be Empty");
//		}
//		if (request.getUserName() == null || request.getPassword() == null) {
//			throw new IllegalArgumentException("Missing  userName or Password");
//		}
		try {
			UsernamePasswordAuthenticationToken tokenn = new UsernamePasswordAuthenticationToken(request.getUserName(),
					request.getPassword());
			// Before authentication
			System.out.println("Token before authentication: " + tokenn);

			Authentication authenticatedToken = utilService.getAuthenticationManager().authenticate(tokenn);
			// after authentication
			System.out.println("Token after authentication: " + authenticatedToken);

			UserDetails userDetails = (UserDetails) authenticatedToken.getPrincipal();
			Optional<Users> users = userRepository.findByuserName(userDetails.getUsername());
			if (users.isPresent() && users.get().getIsActive()) {
				String token = utilService.getJwtService().generateToken(userDetails);
				return new AuthenticationResponse(token);
			} else {
				throw new IllegalStateException("User is inactive. Please contact Admin.");
			}
		} catch (AuthenticationException e) {
			throw new BadCredentialsException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Users updateUser(String uuid, Users users) {

		Users user = userRepository.findByUuid(uuid)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + uuid));

//		if (users.getUserName() == null || users.getPassword() == null || users.getRole() == null
//				|| users.getEmail() == null) {
//			throw new IllegalArgumentException("Username, password, email, and role must be provided.");
//		}

		if (!PhoneNumberValidation.isValid(users.getPhoneNumber())) {
			throw new IllegalArgumentException("Invalid phone number format");
		}

		if (!users.getEmail().contains("@gmail.com") || !users.getEmail().endsWith(".com")) {
			throw new IllegalArgumentException("Invalid email, Please check your email.");
		}
		Optional<Users> existingPhone = userRepository.findByPhoneNumberAndIsActive(users.getPhoneNumber(), true);
		if (existingPhone.isPresent() && !existingPhone.get().getUuid().equals(uuid)) {
			throw new IllegalArgumentException("Phone number is already in use by another account.");
		}
		Optional<Users> existingUserName = userRepository.findByUserName(users.getUserName());
		if (existingUserName.isPresent() && !existingUserName.get().getUuid().equals(uuid)) {
			throw new IllegalArgumentException("A user with this userName already exist");
		}

		if (user.getRole().equals(Role.ADMIN)) {
			user.setName(users.getName());
			user.setEmail(users.getEmail());
			user.setPhoneNumber(users.getPhoneNumber());
			user.setUserName(users.getUserName());
			user.setPassword(utilService.getPasswordEncoder().encode(users.getPassword()));
			user.setRole(users.getRole());
			user.setIsActive(true);
		} else {
			user.setName(users.getName());
			user.setEmail(users.getEmail());
			user.setPhoneNumber(users.getPhoneNumber());
			user.setUserName(users.getUserName());
			user.setPassword(utilService.getPasswordEncoder().encode(users.getPassword()));
			if (users.getRole().equals(Role.ADMIN)) {
				throw new IllegalStateException("Invalid Role");
			}
			user.setRole(users.getRole());
			user.setIsActive(true);
		}
		return userRepository.save(user);
	}

	@Override
	public Optional<String> deactiveUser(String Uuid) {
		Users deletedUser = userRepository.findByUuid(Uuid)
				.orElseThrow(() -> new IllegalArgumentException(" user not found with uuid"));
		if (deletedUser.getRole().equals(Role.ADMIN)) {
			throw new IllegalStateException("Yor are trying to delete a Admin !!!!.");
		}
		deletedUser.setIsActive(false);
		userRepository.save(deletedUser);
		return Optional.of(deletedUser.getName());
	}

	@Override
	public List<UsersDTO> getAllUsers() {
		List<Users> users = userRepository.findByIsActiveTrueAndRoleNot(Role.ADMIN);

		if (users.isEmpty()) {
			throw new ResourceNotFoundException("No Users Found");
		}
		return users.stream() // Convert list to Stream
				.map(this::convertToDTO) // Convert each Users to UsersDTO
				.collect(Collectors.toList()); // Collect results into a List<UsersDTO>
	}

	@Override
	public List<UsersDTO> getAllActiveUsers() {
		return userRepository.findByIsActiveTrue().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<UsersDTO> getAllInactiveUsers() {
		return userRepository.findByIsActiveFalse().stream().map(this::convertToDTO) // Convert each User to UserDTO
				.collect(Collectors.toList());
	}

	@Override
	public List<UsersDTO> getAllTrainees() {
		return userRepository.findAllByRole(Role.TRAINEE).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<UsersDTO> getAllAttendees() {
		return userRepository.findAllByRole(Role.ATTENDEE).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Users> getUsersByUuid(String Uuid) {
		Users users1 = userRepository.findByUuid(Uuid)
				.orElseThrow(() -> new IllegalArgumentException("No user founs this uuid"));
		return Optional.of(users1);

	}

	@Override
	public Optional<Users> findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public List<UsersDTO> findAllByNameContaining(String name) {
		return userRepository.findByNameContainingAndIsActive(name, true).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}
}
