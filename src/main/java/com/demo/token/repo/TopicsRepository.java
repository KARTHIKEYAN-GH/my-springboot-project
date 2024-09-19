package com.demo.token.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.token.dto.TopicCategoryProjection;
import com.demo.token.model.Category;
import com.demo.token.model.Topics;

@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long> {
	/**
	 * To find a topics names and topic's uuid based on category uuid and
	 * 
	 * @param categoryUuid
	 * @return List of TopicCategoryProjection
	 */
	@Query("SELECT t.name AS topicName, t.uuid AS topicUuid, c.name AS categoryName "
			+ "FROM Topics t JOIN Category c ON t.category.id = c.id " + "WHERE c.uuid = :categoryUuid")
	List<TopicCategoryProjection> findTopicAndCategoryNameByCategoryUuid(@Param("categoryUuid") String categoryUuid);

	/**
	 * To find a Topic
	 * 
	 * @param id
	 * @param categoryId
	 * @return Topics
	 */
	//Optional<Topics> findByIdAndCategoryId(Long id, Long categoryId);

	@Query("SELECT t FROM Topics t WHERE t.id = :id AND t.category.uuid = :categoryUuid")
	Optional<Topics> findByIdAndCategoryUuid(@Param("id") String topicsUuid,@Param("categoryUuid") String categoryUuid);

	/**
	 * Too find read topic's description
	 * 
	 * @param topicsUuid
	 * @return Topic's Description
	 */
	@Query(nativeQuery = true, value = "SELECT t.description FROM topics t WHERE t.uuid = :topicsUuid")
	Optional<String> findDescriptionByTopicsUuid(@Param("topicsUuid") String topicsUuid);

	/**
	 * To find a Topic
	 * 
	 * @param topicsUuid
	 * @return Topics
	 */
	Optional<Topics> findByUuid(String uuid);

	/**
	 * To find a topics based on category
	 * 
	 * @param category
	 * @return topics details
	 */
	Optional<Topics> findByCategory(Category category);

	/**
	 * To find a topics under category
	 * 
	 * @param categoryUuid
	 * @return List of Topics
	 */
	List<Topics> findByCategoryUuid(String categoryUuid);
	
	/**
	 * Checks if a record exists with the given UUID.
	 *
	 * @param uuid The UUID of the record to check for existence.
	 * @return {@code true} if a record with the specified UUID exists, {@code false} otherwise.
	 */
	boolean existsByUuid(String uuid);
	
	/**
	 * To find All Topics  
	 * @return List of Topics By  IsActive is true 
	 */
	List<Topics> findByIsActiveTrue();
	
}
