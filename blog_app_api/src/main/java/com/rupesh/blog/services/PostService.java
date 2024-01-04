package com.rupesh.blog.services;

import java.util.List;

import com.rupesh.blog.payloads.PostDto;
import com.rupesh.blog.payloads.PostResponse;

public interface PostService {
	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer PostId);
	
	//get all posts
	//PostResponse getAllPost(Integer pageNumber,Integer pageSize);
	
	//get single posts
	PostDto getPostById(Integer postId);
	
	//get all post by category
	//List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all post by user
	//List<PostDto> getPostsByUSer(Integer userId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);


	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageize);

	PostResponse getPostsByUSer(Integer userId, Integer pageNumber, Integer pageize);

	List<PostDto> getAllPost();

	PostResponse getAllPost(Integer pageNumber, Integer pageize, String sortBy, String sortDir);
	
	
}
