package com.demo.token.utility;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demo.token.service.MailService;
import com.demo.token.serviceImpl.JwtService;

public class Util {
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

	public Util(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService,
			MailService mailService, ModelMapper modelMapper) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.mailService = mailService;
		this.modelMapper = modelMapper;
	}
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
	 * Used to map fields from one object to another based on their field names and
	 * types.
	 */
	private final ModelMapper modelMapper;

}
