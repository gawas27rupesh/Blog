package com.rupesh.blog.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryDtoTest {
	
	CategoryDto categoryDto=new CategoryDto();

	@Test
	void setter() {
		categoryDto.setCategoryId(1);
		categoryDto.setCategoryTitle("abc");
		categoryDto.setCategoryDescription("abc");
	}
	
	@Test
	void getter() {
		categoryDto.getCategoryId();
		categoryDto.getCategoryTitle();
		categoryDto.getCategoryDescription();
	}
	
}
