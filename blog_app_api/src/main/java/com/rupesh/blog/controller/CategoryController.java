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
import org.springframework.web.bind.annotation.RestController;

import com.rupesh.blog.dto.ApiResponse;
import com.rupesh.blog.dto.CategoryDto;
import com.rupesh.blog.entities.Category;
import com.rupesh.blog.services.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping//ok
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		log.info("Create Category");
		CategoryDto createCategoryDto = categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);

	}

	@PutMapping("/{categoryId}")//ok
	public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") Integer categoryId) {
		log.info("Update Category");
		CategoryDto updateCategory = categoryService.updateCategory(categoryDto,categoryId);
		return ResponseEntity.ok(updateCategory);

	}

	@DeleteMapping("/{categoryId}")//ok
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		log.info("Delete Category");
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("Category deleted Successfully", true), HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")//ok
	public ResponseEntity<Category> getCategory(@Valid @PathVariable("categoryId") Integer categoryId) {
		log.info("Fetch Category");
		Category getCat = categoryService.getCategory(categoryId);
		return new ResponseEntity<>(getCat, HttpStatus.OK);
	}

	@GetMapping//ok
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		log.info("Fetch All Category");
		List<CategoryDto> allCat = categoryService.getCategories();
		return ResponseEntity.ok(allCat);
	}
}
