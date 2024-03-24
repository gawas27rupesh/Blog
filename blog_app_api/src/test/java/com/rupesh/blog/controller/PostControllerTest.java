package com.rupesh.blog.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupesh.blog.dto.PostDto;
import com.rupesh.blog.dto.PostResponseDto;
import com.rupesh.blog.services.PostService;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
	
	@InjectMocks
	PostController postController;
	
	@Mock
	PostService postService;
	
	@Mock
	MultipartFile file;
	
	@Mock
	ObjectMapper mapper;
	
	//@Test
	void createPostTest() throws IOException {
		PostDto createPost=new PostDto();
		when(postService.createPost(createPost, file, 1, 1)).thenReturn(createPost);
		//assertNotNull(postController.createPost(file,"abc", 1, 1));
	}

	
	@Test
	void getPostByUserTest() {
		PostResponseDto postResponseDto=mock(PostResponseDto.class);
		when(postService.getPostsByUSer(1, 1, 1)).thenReturn(postResponseDto);
		assertNotNull(postController.getPostByUser(1, 1, 1));
		when(postService.getPostsByUSer(1, 1, 1)).thenThrow(NullPointerException.class);
		assertNotNull(postController.getPostByUser(	1, 1, 1));
	}
	
	@Test
	void getPostByCategoryTest() {
		PostResponseDto postResponseDto=mock(PostResponseDto.class);
		when(postService.getPostsByCategory(1, 1, 1)).thenReturn(postResponseDto);
		assertNotNull(postController.getPostByCategory(	1, 1, 1));
		when(postService.getPostsByCategory(1, 1, 1)).thenThrow(NullPointerException.class);
		assertNotNull(postController.getPostByCategory(	1, 1, 1));
	}
	
	@Test
	void getAllPostTest() {
		PostResponseDto postResponseDto=mock(PostResponseDto.class);
		when(postService.getAllPost(1, 1, "asc", "dir")).thenReturn(postResponseDto);
		assertNotNull(postController.getAllPost(1, 1, "asc", "dir"));
		when(postService.getAllPost(1, 1, "asc", "dir")).thenThrow(NullPointerException.class);
		assertNotNull(postController.getAllPost(1, 1, "asc", "dir"));
	
	}
	
	@Test
	void getPostByIdTest() {
		PostDto postDto=mock(PostDto.class);
		when(postService.getPostById(1)).thenReturn(postDto);
		assertNotNull(postController.getPostById(1));
		when(postService.getPostById(1)).thenThrow(NullPointerException.class);
		assertNotNull(postController.getPostById(1));
	}

	@Test
	void deletePostTest() {
		when(postService.deletePost(1)).thenReturn("done");
		assertNotNull(postController.deletePost(1));
		when(postService.deletePost(1)).thenThrow(NullPointerException.class);
		assertNotNull(postController.deletePost(1));
	
	}
	
	@Test
	void updatePodTest() {
		PostDto postDto=mock(PostDto.class);
		when(postService.updatePost(postDto, 1)).thenReturn(postDto);
		assertNotNull(postController.updatePodt(postDto, 1));
		when(postService.updatePost(postDto, 1)).thenThrow(NullPointerException.class);
		assertNotNull(postController.updatePodt(postDto, 1));
	
	}

}
