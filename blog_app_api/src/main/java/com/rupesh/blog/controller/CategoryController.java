package com.rupesh.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rupesh.blog.dto.ApiResponse;
import com.rupesh.blog.dto.CategoryDto;
import com.rupesh.blog.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	private static CategoryService categoryService;

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createCategoryDto = categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);

	}

	// update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") Integer categoryId) {
		CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updateCategory);

	}

	// Delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("Category deleted Successfully", true), HttpStatus.OK);
	}

	// get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@Valid @PathVariable("categoryId") Integer categoryId) {
		CategoryDto getCat = categoryService.getCategory(categoryId);
		return new ResponseEntity<>(getCat, HttpStatus.OK);

	}

	// getAllCategory
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> allCat = categoryService.getCategories();
		return ResponseEntity.ok(allCat);

	}
}
