package com.demo.token.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.token.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	/**
	 * To find a category by its uuid
	 * @param uuid
	 * @return category details
	 */
	Optional<Category> findByUuid(String uuid);

	/**
	 * To check a whether category present or not 
	 * @param uuid
	 * @return true or false
	 */
	boolean existsByUuid(String uuid);
	
	@Modifying
    @Query("DELETE FROM Category c WHERE c.uuid = :uuid")
    void deleteByUuid(@Param("uuid") String uuid);
	
	
	List<Category>findByIsActiveTrue();
	
}
