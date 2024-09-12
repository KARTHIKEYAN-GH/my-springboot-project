package com.demo.token.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for category details.
 * <p>
 * This class is used to transfer category information, including UUID, name,
 * and the creator of the category.
 */
@Data
public class CatregoryDTO {

	/**
	 * Unique identifier for the category.
	 */
	private String uuid;

	/**
	 * Name of the category.
	 */
	private String name;

	/**
	 * The username or identifier of the creator of the category.
	 */
	private String createdBY;

	/**
	 * Default constructor.
	 * <p>
	 * Initializes a new instance of CatregoryDTO with default values.
	 */
	public CatregoryDTO() {
		super();
	}

	/**
	 * Parameterized constructor for creating a CatregoryDTO object with all fields.
	 *
	 * @param uuid      the unique identifier of the category
	 * @param name      the name of the category
	 * @param createdBY the username or identifier of the creator
	 */
	public CatregoryDTO(String uuid, String name, String createdBY) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.createdBY = createdBY;
	}

	/**
	 * Gets the unique identifier of the category.
	 *
	 * @return the UUID of the category
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the unique identifier of the category.
	 *
	 * @param uuid the UUID to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets the name of the category.
	 *
	 * @return the name of the category
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the category.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the username or identifier of the creator of the category.
	 *
	 * @return the creator's username or identifier
	 */
	public String getCreatedBY() {
		return createdBY;
	}

	/**
	 * Sets the username or identifier of the creator of the category.
	 *
	 * @param createdBY the creator's username or identifier to set
	 */
	public void setCreatedBY(String createdBY) {
		this.createdBY = createdBY;
	}
}
