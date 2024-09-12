package com.demo.token.dto;

import com.demo.token.model.Users.Role;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for Users.
 * <p>
 * This class is used to transfer user data between different layers of the application
 * without exposing the actual entity.
 */
@Data
public class UsersDTO {

    /**
     * Unique identifier for the user.
     */
    private String uuid;

    /**
     * Name of the user.
     */
    private String name;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Username used for authentication.
     */
    private String userName;

    /**
     * Role of the user (e.g., ADMIN, TRAINEE, ATTENDEE).
     */
    private Role role;

    /**
     * Indicates whether the user is active.
     */
    private boolean isActive;

    /**
     * A custom message related to the user (e.g., status updates).
     */
    private String message;

    /**
     * Default constructor.
     */
    public UsersDTO() {
        super();
    }

    /**
     * Parameterized constructor for creating a UsersDTO object with all fields.
     *
     * @param uuid      the unique identifier of the user
     * @param name      the name of the user
     * @param email     the email address of the user
     * @param userName  the username of the user
     * @param role      the role of the user (e.g., ADMIN, TRAINEE, ATTENDEE)
     * @param isActive  indicates if the user is active
     * @param message   a custom message related to the user
     */
    public UsersDTO(String uuid, String name, String email, String userName, Role role, Boolean isActive, String message) {
        super();
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.role = role;
        this.isActive = isActive;
        this.message = message;
    }

    // Getters and Setters

    /**
     * Gets the UUID of the user.
     *
     * @return the UUID of the user
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the UUID of the user.
     *
     * @param uuid the UUID to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the userName of the user.
     *
     * @return the userName of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the userName of the user.
     *
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the active status of the user.
     *
     * @return true if the user is active, otherwise false
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the active status of the user.
     *
     * @param isActive the active status to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the custom message related to the user.
     *
     * @return the message related to the user
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the custom message related to the user.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
