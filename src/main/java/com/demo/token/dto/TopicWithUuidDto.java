package com.demo.token.dto;

/**
 * Data Transfer Object (DTO) for topics with UUID.
 * <p>
 * This class is used to transfer topic information that includes the topic name and its unique identifier (UUID).
 */
public class TopicWithUuidDto {

    /**
     * The name of the topic.
     */
    private String topicName;

    /**
     * The unique identifier (UUID) of the topic.
     */
    private String topicUuid;

    /**
     * Parameterized constructor for creating a TopicWithUuidDto object with all fields.
     *
     * @param topicName the name of the topic
     * @param topicUuid the unique identifier (UUID) of the topic
     */
    public TopicWithUuidDto(String topicName, String topicUuid) {
        this.topicName = topicName;
        this.topicUuid = topicUuid;
    }

    // Getters

    /**
     * Gets the name of the topic.
     *
     * @return the name of the topic
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * Gets the unique identifier (UUID) of the topic.
     *
     * @return the unique identifier (UUID) of the topic
     */
    public String getTopicUuid() {
        return topicUuid;
    }
}
