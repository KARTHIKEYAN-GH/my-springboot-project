package com.demo.token.serviceImpl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.token.dto.CatregoryDTO;
import com.demo.token.model.Category;
import com.demo.token.repo.CategoryRepository;
import com.demo.token.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	/**
	 * Used to interact with category table to perform CRUD Operation
	 */
	private final CategoryRepository categoryRepository;

	CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Category addCategory(Category category) {
		category.setActive(true);
		return categoryRepository.save(category);
	}

	public CatregoryDTO convertsToDTO(Category category) {
		return new CatregoryDTO(category.getUuid(), category.getName(), category.getCreatedBY());
	}

//	@Override
//	public Set<CatregoryDTO> getAllCategories() {
//		return categoryRepository.findAll().stream().filter(category -> Boolean.TRUE.equals(category.isActive()))
//				.map(this::convertsToDTO).collect(Collectors.toSet());
//	}
	@Override
	public Set<CatregoryDTO> getAllCategories() {
		return categoryRepository.findByIsActiveTrue().stream().map(this::convertsToDTO).collect(Collectors.toSet());
	}

	@Override
	public Optional<CatregoryDTO> getCategoryByUuid(String categoryUuid) {
		return categoryRepository.findByUuid(categoryUuid).map(this::convertsToDTO);
	}

	@Override
	public Optional<Category> updateCategoryByUuid(String categoryUuid, Category category) {

		Optional<Category> existingcategory = categoryRepository.findByUuid(categoryUuid);

		if (existingcategory.isPresent()) {
			Category updateCategory = existingcategory.get();
			updateCategory.setName(category.getName());
			categoryRepository.save(updateCategory);
			return Optional.of(updateCategory);

		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean deleteCategoryByUuid(String uuid) {

		if (categoryRepository.existsByUuid(uuid)) {
			// categoryRepository.deleteByUuid(uuid);
			Category deletedCategoryId = categoryRepository.findByUuid(uuid)
					.orElseThrow(() -> new RuntimeException("category not found"));
			if (deletedCategoryId.isActive())
				deletedCategoryId.setActive(false);
			categoryRepository.save(deletedCategoryId);
			return true;
		}

		else {
			return false;
		}
	}

	@Override
	public Optional<Category> findByUuid(String categoryUuid) {
		// TODO Auto-generated method stub
		return categoryRepository.findByUuid(categoryUuid);
	}

}
