package com.rupesh.blog.services;

import java.util.List;

import com.rupesh.blog.dto.CategoryDto;

public interface CategoryService {

	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete
	void deleteCategory(Integer categoryId);
	
	
	//get all
	List<CategoryDto> getCategories();

	//get
	CategoryDto getCategory(Integer categoryId);
}
