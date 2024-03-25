package com.rupesh.blog.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.rupesh.blog.dto.CategoryDto;

public interface CategoryService {

	Optional<CategoryDto> createCategory(CategoryDto categoryDto);
	
	String deleteCategory(Integer categoryId);
	
	List<Optional<CategoryDto>> getCategories();

	Optional<CategoryDto> getCategory(Integer categoryId);

	Optional<CategoryDto> updateCategory(@Valid CategoryDto categoryDto, Integer categoryId);
}
