package com.rupesh.blog.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.rupesh.blog.dto.PostDto;
import com.rupesh.blog.dto.PostResponse;

public interface PostService {
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer PostId);
	
	//get single posts
	PostDto getPostById(Integer postId);
	
	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageize);

	PostResponse getPostsByUSer(Integer userId, Integer pageNumber, Integer pageize);

	//List<PostDto> getAllPost();

	PostResponse getAllPost(Integer pageNumber, Integer pageize, String sortBy, String sortDir);

	PostDto createPost(PostDto createPost, MultipartFile file, Integer userId, Integer categoryId) throws IOException;
	
	
}
