package com.demo.token.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for topics.
 * <p>
 * This class is used to transfer topic information including UUID, topic name,
 * and creator details.
 */
@Data
public class TopicsDTO {

	/**
	 * Unique identifier for the topic.
	 */
	private String uuid;

	/**
	 * Name of the topic.
	 */
	private String Topic_Name;

	/**
	 * The username or identifier of the creator of the topic.
	 */
	private String createdBy;

	/**
	 * Parameterized constructor for creating a TopicsDTO object with all fields.
	 *
	 * @param uuid       the unique identifier of the topic
	 * @param Topic_Name the name of the topic
	 * @param createdBy  the username or identifier of the creator
	 */
	public TopicsDTO(String uuid, String Topic_Name, String createdBy) {
		super();
		this.uuid = uuid;
		this.Topic_Name = Topic_Name;
		this.createdBy = createdBy;
	}

	/**
	 * Default constructor.
	 */
	public TopicsDTO() {
		super();
	}

	/**
	 * Gets the unique identifier of the topic.
	 *
	 * @return the UUID of the topic
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the unique identifier of the topic.
	 *
	 * @param uuid the UUID to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets the name of the topic.
	 *
	 * @return the name of the topic
	 */
	public String getTopic_Name() {
		return Topic_Name;
	}

	/**
	 * Sets the name of the topic.
	 *
	 * @param topic_Name the name to set
	 */
	public void setTopic_Name(String topic_Name) {
		this.Topic_Name = topic_Name;
	}

	/**
	 * Gets the username or identifier of the creator of the topic.
	 *
	 * @return the creator's username or identifier
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the username or identifier of the creator of the topic.
	 *
	 * @param createdBy the creator's username or identifier to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
