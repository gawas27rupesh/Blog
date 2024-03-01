package com.rupesh.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.EnumMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rupesh.blog.dto.CommentDto;
import com.rupesh.blog.enums.ApiKey;
import com.rupesh.blog.services.CommentService;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {
	
	@Mock
	CommentService commentService;
	
	@Mock
	CommentDto commentDto;
	
	@Mock
	CommentController commentController;
	
	@Mock
	EnumMap enumMap;
	
	@Test
	void createCommentTest() {
		commentController.createComment(commentDto, 1);
	}
	
	@Test
	void deleteCommentTest() {
		
		
	}
	
	
	
}
