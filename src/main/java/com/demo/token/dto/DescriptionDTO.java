package com.demo.token.dto;

/**
 * Data Transfer Object (DTO) for descriptions.
 * <p>
 * This class is used to transfer description information, typically used
 * for various purposes where a description needs to be encapsulated in a DTO.
 */

public class DescriptionDTO {

    /**
     * The description text.
     */
    private String description;

    /**
     * Default constructor.
     * <p>
     * Initializes a new instance of DescriptionDTO with default values.
     */
    public DescriptionDTO() {
        super();
    }

    /**
     * Parameterized constructor for creating a DescriptionDTO object with a description.
     *
     * @param description the description text to set
     */
    public DescriptionDTO(String description) {
        super();
        this.description = description;
    }

    /**
     * Gets the description text.
     *
     * @return the description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description text.
     *
     * @param description the description text to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
