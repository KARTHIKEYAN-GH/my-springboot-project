package com.demo.token.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Users {
	/**
	 * It is an auto generated value for each user
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * It is an unique id for each user to identify
	 */
	@Column(nullable = false, unique = true, updatable = false)
	private String uuid = UUID.randomUUID().toString();

	/**
	 * Name of the user
	 */
	private String name;

	/**
	 * email for communication
	 */
	private String email;

	/**
	 * unique phoneNumber for each user
	 */
	private String phoneNumber;

	/**
	 * Unique userName for login
	 */
	@Column(name="user_name",nullable = false)
	private String userName;

	/**
	 * password for login
	 */
	private String password;

	/**
	 * Used to represent a user is active or not
	 */
	@Column(name="is_active",nullable = false)
	private Boolean isActive;

	/**
	 *
	 * To identify the count of updated made on Users
	 */
	@Version
	private Long version; // This is the version column for optimistic locking.

	/**
	 * The search field is used to store the search query entered by the user.
	 * 
	 * This field holds the text that the user has typed in to perform a search
	 * operation. It can be used to filter or find relevant results based on the
	 * query provided.
	 */
	private String serach;

	public enum Role {
		ADMIN, TRAINEE, ATTENDEE
	}

	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * To identify the time of Creation
	 */
	@CreatedDate
	@Column(name="created_at",nullable = false, updatable = false)
	private LocalDateTime createdAt;

	/**
	 * To identify the time of updatedAt
	 */
	@LastModifiedDate
	@Column(name="updated_at",nullable = false)
	private LocalDateTime updatedAt;

}
