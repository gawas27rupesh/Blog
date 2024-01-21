package com.rupesh.blog.services;

import java.util.List;

import com.rupesh.blog.dto.CategoryDto;
import com.rupesh.blog.entities.Category;

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
	Category getCategory(Integer categoryId);
}
