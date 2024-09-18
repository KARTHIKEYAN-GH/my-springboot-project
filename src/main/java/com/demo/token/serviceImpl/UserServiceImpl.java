package com.demo.token.serviceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Predicate;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.token.dto.AuthenticationResponse;
import com.demo.token.dto.TopicsDTO;
import com.demo.token.dto.UsersDTO;
import com.demo.token.exception.ResourceNotFoundException;
import com.demo.token.model.Topics;
import com.demo.token.model.Users;
import com.demo.token.model.Users.Role;
import com.demo.token.repo.UserRepository;
import com.demo.token.service.MailService;
import com.demo.token.service.UsersService;
import com.demo.token.validation.EmailValidator;
import com.demo.token.validation.PhoneNumberValidation;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UsersService {
	/**
	 * Repository for performing CRUD operations on Users table. Used to interact
	 * with the database for managing users-related data.
	 */
	private final UserRepository userRepository;

	/**
	 * Encode a provide password. This ensures that user passwords are not stored in
	 * plain text, enhancing security.
	 */

	private final PasswordEncoder passwordEncoder;

	/**
	 * Manage authentication process by provided credentials compare against with
	 * stored credentials
	 */
	private final AuthenticationManager authenticationManager;

	/**
	 * Used to extract role from Authorization Header
	 */
	private final JwtService jwtService;

	/**
	 * Responsible for sending email to user after succssfull registration
	 */
	private final MailService mailService;
	/**
	 * Responsible for automating the conversion between different object models,
	 * Used to map fields from one object to another based on their field names and types.
	 */
	private final ModelMapper modelMapper;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtService jwtService, MailService mailService, ModelMapper modelMapper) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.mailService = mailService;
		this.modelMapper = modelMapper;
	}

	public UsersDTO convertToDTO(Users users) {
		UsersDTO usersDTO= modelMapper.map(users,UsersDTO.class);
		usersDTO.setMessage("User Sucessfully Registered ");
		return usersDTO;
//		return new UsersDTO(users.getUuid(), users.getName(), users.getEmail(), users.getUserName(), users.getRole(),
//				users.getIsActive(),
//				"User Sucessfully Registered , Please check your mail for further access's credentials ");
//		
	}
	
//	public UsersDTO convertsToDTO(Users users) {
//		 return modelMapper.typeMap(users, UsersDTO.class).addMappings(mapper->{mapper.skip(UsersDTO::setMessage);
//		});
//	}
//	
	
	@Override
	@Transactional
	public UsersDTO createUser(Users users) {
		if (users.getUserName() == null || users.getPassword() == null || users.getRole() == null
				|| users.getEmail() == null) {
			throw new IllegalArgumentException("Username, password, email, and role must be provided.");
		}
		if (!PhoneNumberValidation.isValid(users.getPhoneNumber())) {
			throw new IllegalArgumentException("Invalid phone number format.");
		}
		 if(!EmailValidator.isValid(users.getEmail()))
		 {
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

		if(users.getRole().equals(Role.TRAINEE)||users.getRole().equals(Role.ATTENDEE))
		{
		mailService.sendEmail(users.getEmail(), users.getName(), users.getEmail(), users.getPhoneNumber(),
				users.getUserName(), users.getPassword(), users.getRole());
		users.setIsActive(true);
		users.setPassword(passwordEncoder.encode(users.getPassword()));

		Users savedUsers = userRepository.save(users);
		UsersDTO usersDTO=convertToDTO(savedUsers);
		usersDTO.setMessage("User Sucessfully Register as "+"\"" + users.getRole()+ "\""+" please check your mail for login credentials");
		return usersDTO;
		}
		else
		{
			throw new IllegalArgumentException("Please check your role(TRAINEE or ATTENDEE)");

		}
	}
	
	@Override
	public AuthenticationResponse authenticate(Users request) {
		if(request.getUserName()==null || request.getPassword()==null)
		{
			throw new IllegalArgumentException("UserName and Password should not be Empty");
		}
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		Users users = userRepository.findByuserName(request.getUserName()).orElseThrow(()-> new UsernameNotFoundException("No user found given user name"));
		if (users.getIsActive()) {
			String token = jwtService.generateToken(users);
			return new AuthenticationResponse(token);
		} else {

			throw new IllegalStateException("User is inactive. Please contact Admin.");
		}
	}
	
	@Override
	@Transactional
	public Users updateUser(String uuid, Users users) {
		
		Users user = userRepository.findByUuid(uuid)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + uuid));
		
		if (users.getUserName() == null || users.getPassword() == null || users.getRole() == null
				|| users.getEmail() == null) {
			throw new IllegalArgumentException("Username, password, email, and role must be provided.");
		}
		
		if (!PhoneNumberValidation.isValid(users.getPhoneNumber())) {
			throw new IllegalArgumentException("Invalid phone number format");
		}
		
		if (!users.getEmail().contains("@gmail.com")|| !users.getEmail().endsWith(".com")) {
			throw new IllegalArgumentException("Invalid email, Please check your email.");
		}
		Optional<Users> existingPhone = userRepository.findByPhoneNumberAndIsActive(users.getPhoneNumber(), true);
	    if (existingPhone.isPresent() && !existingPhone.get().getUuid().equals(uuid)) {
	        throw new IllegalArgumentException("Phone number is already in use by another account.");
	    }
	    Optional<Users> existingUserName=userRepository.findByUserName(users.getUserName());
	    if(existingUserName.isPresent()&& !existingUserName.get().getUuid().equals(uuid))
	    {
	    	throw new IllegalArgumentException("A user with this userName already exist");
	    }
		
		if(user.getRole().equals(Role.ADMIN))
		{
		user.setName(users.getName());
		user.setEmail(users.getEmail());
		user.setPhoneNumber(users.getPhoneNumber());
		user.setUserName(users.getUserName());
		user.setPassword(passwordEncoder.encode(users.getPassword()));
		user.setRole(users.getRole());
		user.setIsActive(true);
		}
		else {
		user.setName(users.getName());
		user.setEmail(users.getEmail());
		user.setPhoneNumber(users.getPhoneNumber());
		user.setUserName(users.getUserName());
		user.setPassword(passwordEncoder.encode(users.getPassword()));
		if (users.getRole().equals(Role.ADMIN)) {
			throw new IllegalStateException("Invalid Role");
		}
		user.setRole(users.getRole());
		user.setIsActive(true);}
		return userRepository.save(user);
	}
	@Override
	public Optional<String> deactiveUser(String Uuid) {
		Users deletedUser = userRepository.findByUuid(Uuid).orElseThrow(() -> new IllegalArgumentException(" user not found with uuid"));
		if (deletedUser.getRole().equals(Role.ADMIN)) {
			throw new IllegalStateException("Yor are trying to delete a Admin !!!!.");
		}
		deletedUser.setIsActive(false);
		userRepository.save(deletedUser);
		return Optional.of(deletedUser.getName());
	}
	@Override
	public List<UsersDTO> getAllUsers() {
	    List<Users> users = userRepository.findAll();

	    if (users.isEmpty()) {
	        throw new ResourceNotFoundException("No Users Found");
	    }
	    // Convert each Users entity to UsersDTO, omitting users with the ADMIN role
	    return users.stream() 										// Convert list to Stream
	                .filter(user -> !user.getRole().equals(Role.ADMIN)) // Exclude ADMIN users
	                .map(this::convertToDTO) 					// Convert each Users to UsersDTO
	                .collect(Collectors.toList()); 				// Collect results into a List<UsersDTO>
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

		return userRepository.findByUuid(Uuid);
	}

	@Override
	public Optional<Users> findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public List<UsersDTO> findAllByNameContaining(String name) {
		return userRepository.findByNameContaining(name).stream().map(this::convertToDTO).collect(Collectors.toList());
	}
}
