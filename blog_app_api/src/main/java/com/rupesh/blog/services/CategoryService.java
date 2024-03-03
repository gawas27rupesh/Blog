package com.rupesh.blog.services;

import java.util.List;

import javax.validation.Valid;

import com.rupesh.blog.dto.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	String deleteCategory(Integer categoryId);
	
	List<CategoryDto> getCategories();

	CategoryDto getCategory(Integer categoryId);

	CategoryDto updateCategory(@Valid CategoryDto categoryDto, Integer categoryId);
}
