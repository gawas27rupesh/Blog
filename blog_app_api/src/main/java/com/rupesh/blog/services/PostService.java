package com.rupesh.blog.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.rupesh.blog.dto.PostDto;
import com.rupesh.blog.dto.PostResponseDto;

public interface PostService {
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	String deletePost(Integer PostId);
	
	PostDto getPostById(Integer postId);
	
	PostResponseDto getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageize);

	PostResponseDto getPostsByUSer(Integer userId, Integer pageNumber, Integer pageize);

	PostResponseDto getAllPost(Integer pageNumber, Integer pageize, String sortBy, String sortDir);

	PostDto createPost(PostDto createPost, MultipartFile file, Integer userId, Integer categoryId) throws IOException;
	
}
