package com.rupesh.blog.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rupesh.blog.dto.CommentDto;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
	
	@InjectMocks
	CommentServiceImpl commentServiceImpl;

	@Test
	void createCommentTest() {
		//CommentDto commentDto=new CommentDto();
		//assertNotNull(commentServiceImpl.createComment(commentDto, 1));
	}

}
