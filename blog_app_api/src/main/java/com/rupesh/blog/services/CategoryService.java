package com.rupesh.blog.services;

import java.util.List;

import javax.validation.Valid;

import com.rupesh.blog.dto.CategoryDto;
import com.rupesh.blog.entities.Category;

public interface CategoryService {

	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	
	//delete
	void deleteCategory(Integer categoryId);
	
	
	//get all
	List<CategoryDto> getCategories();

	//get
	Category getCategory(Integer categoryId);

	CategoryDto updateCategory(@Valid CategoryDto categoryDto, Integer categoryId);
}
