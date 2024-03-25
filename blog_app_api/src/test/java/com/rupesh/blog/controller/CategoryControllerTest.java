//package com.rupesh.blog.controller;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.rupesh.blog.dto.CategoryDto;
//import com.rupesh.blog.services.CategoryService;
//
//@ExtendWith(MockitoExtension.class)
//class CategoryControllerTest {
//	
//	@InjectMocks
//	CategoryController categoryController;
//	
//	@Mock
//	CategoryService categoryService;
//
//	@Test
//	void createCategoryTest() {
//		CategoryDto categoryDto=mock(CategoryDto.class);
//		when(categoryService.createCategory(categoryDto)).thenReturn(categoryDto);
//		assertNotNull(categoryController.createCategory(categoryDto));
//		when(categoryService.createCategory(categoryDto)).thenThrow(NullPointerException.class);
//		assertNotNull(categoryController.createCategory(categoryDto));
//	
//	}
//	
//	@Test
//	void updateUserTest() {
//		CategoryDto categoryDto=mock(CategoryDto.class);
//		when(categoryService.updateCategory(categoryDto, 1)).thenReturn(categoryDto);
//		assertNotNull(categoryController.updateUser(categoryDto, 1));
//		when(categoryService.updateCategory(categoryDto, 1)).thenThrow(NullPointerException.class);
//		assertNotNull(categoryController.updateUser(categoryDto, 1));
//	
//	}
//	
//	@Test
//	void deleteCategoryTest() {
//		when(categoryService.deleteCategory(1)).thenReturn("done");
//		assertNotNull(categoryController.deleteCategory(1));
//		when(categoryService.deleteCategory(1)).thenThrow(NullPointerException.class);
//		assertNotNull(categoryController.deleteCategory(1));
//	
//	}
//	
//	@Test
//	void getCategoryTest() {
//		CategoryDto categoryDto=mock(CategoryDto.class);
//		when(categoryService.getCategory(1)).thenReturn(categoryDto);
//		assertNotNull(categoryController.getCategory(1));
//		when(categoryService.getCategory(1)).thenThrow(NullPointerException.class);
//		assertNotNull(categoryController.getCategory(1));
//
//	}
//	
//	@Test
//	void getAllCategoryTest() {
//		List<CategoryDto> catList=new ArrayList<>();
//		when(categoryService.getCategories()).thenReturn(catList);
//		assertNotNull(categoryController.getAllCategory());
//		when(categoryService.getCategories()).thenThrow(NullPointerException.class);
//		assertNotNull(categoryController.getAllCategory());
//	
//	}
//
//}
