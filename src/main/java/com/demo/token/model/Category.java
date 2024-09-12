package com.demo.token.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Category {
	/**
	 * It is an auto generated value for each Topic
	 * This is  Category Model
	 * Updated codeee
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * It is an unique id for each category to identify
	 */
    @Column(nullable = false, unique = true, updatable = false)
	private String uuid=UUID.randomUUID().toString();
	
    /**
	 * Name of the category
	 */
	private String name;
	
	/**
	 * Topics are belongs to category's uuid
	 */
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Topics> topics;
	
	/**
	 * To identify the ,who created the category
	 */
	@CreatedBy
	@Column(name = "created_by")
	private String createdBY;
	
	/**
	 * To identify the ,who updates the category(ADMIN or TRAINEE)
	 */
	@LastModifiedBy
	private String updatedBy;

	/**
	 * To identify the time of creation
	 */
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	/**
	 * To identify the time of updates made on category
	 */
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	/**
	 *
	 * To identify the count of updated made on topics
	 */
	@Version
	private Long version;
	
	
	@Column(nullable = false)
	private boolean isActive;
	
	
}
