package com.demo.token.model;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Immutable // This makes the entity read-only
@Table(name = "topic_summary_view") // This should match the name of view script
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TopicSummaryView {

	/**
	 * It is an auto generated value for each view
	 */
	@Id
	@Column(name = "id") // Ensure this matches the primary key column of the view
	private Long id;

	/**
	 * Name of the topic
	 */
	@Column(name = "topics_name")
	private String topicsName;

	/**
	 * This field represents time of creation of topics
	 */
	@Column(name = "created_at")
	private String createdAt; // Adjust the type according to your database column type

	/**
	 * To identify the topic's category
	 */
	@Column(name = "category_name")
	private String categoryName;

}
