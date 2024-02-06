package com.rupesh.blog.controller;

import static com.rupesh.blog.enums.ApiKey.DATA;
import static com.rupesh.blog.enums.ApiKey.SUCCESS;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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
import com.rupesh.blog.enums.ApiKey;
import com.rupesh.blog.services.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<EnumMap<ApiKey, Object>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Create Category");
			CategoryDto createCategoryDto = categoryService.createCategory(categoryDto);
			map.put(DATA, createCategoryDto);
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Category add Error.");
		}
		return created(fromCurrentContextPath().build().toUri()).body(map);
	}

	@PutMapping("/{categoryId}")//ok
	public ResponseEntity<EnumMap<ApiKey, Object>> updateUser(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("categoryId") Integer categoryId) {
		EnumMap<ApiKey, Object> map=new EnumMap<>(ApiKey.class);
		try {
			log.info("Update Category");
			CategoryDto updateCategory = categoryService.updateCategory(categoryDto,categoryId);
			map.put(DATA, updateCategory);
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Error Update Category");
		}
		return ResponseEntity.ok(map);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<EnumMap<ApiKey, Object>> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		EnumMap<ApiKey, Object> map=new EnumMap<>(ApiKey.class);
		try {
			log.info("Delete Category");
			categoryService.deleteCategory(categoryId);
			map.put(DATA, "Category deleted Successfully");
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Error Delete Category");
		}
		return ResponseEntity.ok(map);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<Map<ApiKey, Object>> getCategory(@Valid @PathVariable("categoryId") Integer categoryId) {
		Map<ApiKey, Object> map=new EnumMap<>(ApiKey.class);
		try {
			log.info("Fetch Category");
			Category getCat = categoryService.getCategory(categoryId);
			map.put(DATA, getCat);
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Error Fetch Category");
		}
		return ResponseEntity.ok(map);
	}

	@GetMapping
	public ResponseEntity<EnumMap<ApiKey, Object>> getAllCategory() {
		EnumMap<ApiKey, Object> map=new EnumMap<>(ApiKey.class);
		try {
			log.info("Fetch All Category");
			List<CategoryDto> allCat = categoryService.getCategories();
			map.put(DATA, allCat);
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.info("Error Fetch All Category");
		}
		return ResponseEntity.ok(map);
	}
}
