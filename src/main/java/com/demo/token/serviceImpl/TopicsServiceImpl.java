package com.demo.token.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.token.dto.CategoryWithTopicsDto;
import com.demo.token.dto.DescriptionDTO;
import com.demo.token.dto.TopicCategoryProjection;
import com.demo.token.dto.TopicWithUuidDto;
import com.demo.token.dto.TopicsDTO;
import com.demo.token.exception.ResourceNotFoundException;
import com.demo.token.exception.TopicNotFoundException;
import com.demo.token.model.Category;
import com.demo.token.model.Topics;
import com.demo.token.model.TopicsReadStatus;
import com.demo.token.model.Users;
import com.demo.token.model.Users.Role;
import com.demo.token.repo.TopicsRepository;
import com.demo.token.service.CategoryService;
import com.demo.token.service.TopicsReadStatusService;
import com.demo.token.service.TopicsService;
import com.demo.token.service.UsersService;

@Service
public class TopicsServiceImpl implements TopicsService {
	/**
	 * extracting user information from access token
	 */
	@Autowired
	private final JwtService jwtservice;

	/**
	 * Repository for performing CRUD operations on Topics. Used to interact with
	 * the database for managing topics-related data.
	 */

	private final TopicsRepository topicsRepository;

	/**
	 * Service for handling business logic related to categories. Provides methods
	 * for managing categories that topics belong to.
	 */
	private final CategoryService categoryService;

	/**
	 * Service for handling operations related to users. Provides functionality to
	 * retrieve user information and manage user-related actions.
	 */
	private final UsersService usersService;

	/**
	 * Service for managing read statuses of topics by users. Used to track which
	 * topics have been read by which users, and to update read timestamps. This
	 * helps in implementing features like marking topics as read.
	 */
	private final TopicsReadStatusService topicsReadStatusService;
	/**
	 * Responsible for automating the conversion between different object models,
	 * Used to map fields from one object to another based on their field names and types.
	 */
	private final ModelMapper modelmapper;

	public TopicsServiceImpl(TopicsRepository topicsRepository, JwtService jwtservice, UsersService usersService,
			CategoryService categoryService, TopicsReadStatusService topicsReadStatusService, ModelMapper modelmapper) {
		super();
		this.jwtservice = jwtservice;
		this.topicsRepository = topicsRepository;
		this.categoryService = categoryService;
		this.usersService = usersService;
		this.topicsReadStatusService = topicsReadStatusService;
		this.modelmapper = modelmapper;
	}

	
	@Override
	public TopicsDTO convertsToDTO(Topics topics) {
		//return new TopicsDTO(topics.getUuid(), topics.getName(), topics.getCreatedBy());
	    modelmapper.typeMap(Topics.class, TopicsDTO.class).addMappings(mapper -> {
	        mapper.map(Topics::getName, TopicsDTO::setTopic_Name);
	    });

	    // Perform the mapping
	    return modelmapper.map(topics, TopicsDTO.class);
	}


	public List<Topics> getTopicsbycategoryUuid(String Uuid) {
		return topicsRepository.findByCategoryUuid(Uuid);

	}

	public TopicsDTO addTopics(String categoryUuid, String name, String description) {
		if(name==null ||description==null)
		{
			throw new IllegalArgumentException("please provide name and description for topic");
		}
		Optional<Category> category = categoryService.findByUuid(categoryUuid);
		if (category.isPresent()) {
			Category categories = category.get();
			Topics topics = new Topics();
			topics.setName(name);
			topics.setDescription(description);
			topics.setCategory(categories);
			topics.setActive(true);
			Topics savedtopics= topicsRepository.save(topics);
			 return convertsToDTO(savedtopics);
		} else {
			throw new IllegalArgumentException("Category not found with UUID: " + categoryUuid);
		}
	}

	@Override
	public List<TopicsDTO> getAllTopics() {
		List<TopicsDTO> Extopics = topicsRepository.findByIsActiveTrue().stream().map(this::convertsToDTO)
				.collect(Collectors.toList());
		if (Extopics.isEmpty()) {
			throw new IllegalArgumentException("Topcs not found ");
		}
		return Extopics;
	}

	@Override
	public CategoryWithTopicsDto getTopicsWithCategory(String categoryUuid) {
		List<TopicCategoryProjection> results = topicsRepository.findTopicAndCategoryNameByCategoryUuid(categoryUuid);

		if (results.isEmpty()) {
			throw new ResourceNotFoundException("Topic not found with this category UUID " + categoryUuid);
		}

		String categoryName = results.get(0).getCategoryName();

		List<TopicWithUuidDto> topics = results.stream()
				.map(projection -> new TopicWithUuidDto(projection.getTopicName(), projection.getTopicUuid()))
				.collect(Collectors.toList());

		return new CategoryWithTopicsDto(categoryUuid, categoryName, topics);
	}

	@Override
	public Optional<String> updateTopics(String topicsUuid, Topics topics) {
		Optional<Topics> existingTopic = topicsRepository.findByUuid(topicsUuid);

		if (existingTopic.isPresent()) {
			Topics existingTopicname = existingTopic.get();
			existingTopicname.setName(topics.getName());
			topicsRepository.save(existingTopicname);
			return Optional.of(existingTopicname.getName());
		} else {
			throw new ResourceNotFoundException("Topic not found with UUID " + topicsUuid);
		}
	}

	public DescriptionDTO convertsDescriptionToDTO(String description) {
		return new DescriptionDTO(description);
	}

	@Override
	public Optional<DescriptionDTO> findAndMarkDescriptionAsReadByTopicsUuid(String topicsUuid, String token) {
		// Fetch the description for the given topics UUID and convert it to a
		// DescriptionDTO
		Optional<DescriptionDTO> description = topicsRepository.findDescriptionByTopicsUuid(topicsUuid).stream()
				.map(this::convertsDescriptionToDTO) // Convert each description to DescriptionDTO
				.findFirst(); // Get the first description, if present

		if (description.isPresent()) {
			// Extract the username from the JWT token
			String username = jwtservice.extractUserName(token);

			// Find the user by username, throw an exception if not found
			Users users = usersService.findByUserName(username)
					.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

			// Find the topic by UUID, throw an exception if not found
			Topics topic = topicsRepository.findByUuid(topicsUuid)
					.orElseThrow(() -> new TopicNotFoundException("Topic not found"));

			// Check if the role of the user is ATTENDEE
			if (users.getRole().equals(Role.ATTENDEE)) {
				// Check if there's an existing read status for this user and topic
				Optional<TopicsReadStatus> existingReadStatus = topicsReadStatusService
						.getReadStatusByTopicsUuidAndUserUuid(topicsUuid, users.getUuid());

				if (existingReadStatus.isPresent()) {
					// Update the existing read status with the new read time
					TopicsReadStatus readStatus = existingReadStatus.get();
					readStatus.setReadAt(LocalDateTime.now());
					topicsReadStatusService.saveStatus(readStatus); // Save the updated read status
				} else {
					// Create a new read status
					TopicsReadStatus readStatus = new TopicsReadStatus();
					readStatus.setTopicsUuid(topicsUuid); // Set the topic UUID
					readStatus.setTopics(topic); // Set the topic entity
					readStatus.setUsersUuid(users.getUuid()); // Set the user UUID
					readStatus.setUserName(users.getUserName()); // Set the username
					readStatus.setRole(users.getRole()); // Set the user role
					readStatus.setReadAt(LocalDateTime.now()); // Set the current timestamp
					readStatus.setUsers(users);
					readStatus.setVersion(readStatus.getVersion() + 1);
					topicsReadStatusService.saveStatus(readStatus); // Save the new read status
				}
			}

			return description;
		}
		// Return an empty Optional if no description was found
		return Optional.empty();
	}

	@Override
	public boolean deleteTopicsByUuid(String uuid) {

		if (topicsRepository.existsByUuid(uuid)) {
			// categoryRepository.deleteByUuid(uuid);
			Topics deletedTopicsId = topicsRepository.findByUuid(uuid)
					.orElseThrow(() -> new RuntimeException("Topics not found"));
			if (deletedTopicsId.isActive())
				deletedTopicsId.setActive(false);
			topicsRepository.save(deletedTopicsId);
			return true;
		} else {
			return false;
		}
	}

}
