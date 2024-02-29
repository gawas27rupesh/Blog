package com.rupesh.blog.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentDtoTest {
	
	CommentDto commentDto=new CommentDto();

	@Test
	void setter() {
		commentDto.setId(1);
		commentDto.setContent("abc");
	}
	
	@Test
	void getter() {
		commentDto.getId();
		commentDto.getContent();
	}
	
}
