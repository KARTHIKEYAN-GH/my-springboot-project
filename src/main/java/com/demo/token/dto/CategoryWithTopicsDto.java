package com.demo.token.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) for categories with their associated topics.
 * <p>
 * This class is used to transfer category information along with a list of topics
 * that belong to the category. It encapsulates the details of the category and its
 * related topics.
 */
public class CategoryWithTopicsDto {

    /**
     * Unique identifier for the category.
     */
    private String categoryUuid;

    /**
     * Name of the category.
     */
    private String categoryName;

    /**
     * List of topics associated with the category.
     */
    private List<TopicWithUuidDto> topics;

    /**
     * Parameterized constructor for creating a CategoryWithTopicsDto object with all fields.
     *
     * @param categoryUuid the unique identifier of the category
     * @param categoryName the name of the category
     * @param topics       the list of topics associated with the category
     */
    public CategoryWithTopicsDto(String categoryUuid, String categoryName, List<TopicWithUuidDto> topics) {
        this.categoryUuid = categoryUuid;
        this.categoryName = categoryName;
        this.topics = topics;
    }

    /**
     * Gets the unique identifier of the category.
     *
     * @return the UUID of the category
     */
    public String getCategoryUuid() {
        return categoryUuid;
    }

    /**
     * Sets the unique identifier of the category.
     *
     * @param categoryUuid the UUID to set
     */
    public void setCategoryUuid(String categoryUuid) {
        this.categoryUuid = categoryUuid;
    }

    /**
     * Gets the name of the category.
     *
     * @return the name of the category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the name of the category.
     *
     * @param categoryName the name to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the list of topics associated with the category.
     *
     * @return the list of topics
     */
    public List<TopicWithUuidDto> getTopics() {
        return topics;
    }

    /**
     * Sets the list of topics associated with the category.
     *
     * @param topics the list of topics to set
     */
    public void setTopics(List<TopicWithUuidDto> topics) {
        this.topics = topics;
    }
}
