package com.rupesh.blog.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupesh.blog.config.AppConstants;
import com.rupesh.blog.dto.ApiResponse;
import com.rupesh.blog.dto.PostDto;
import com.rupesh.blog.dto.PostResponse;
import com.rupesh.blog.services.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

	private final PostService postService;
	private final ObjectMapper mapper;

	// create
	@PostMapping(value = "/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestParam("image") MultipartFile file,
			@RequestParam("userData") String userData, @PathVariable Integer userId, @PathVariable Integer categoryId)
			throws IOException {
		PostDto createPost = mapper.readValue(userData, PostDto.class);
		log.info("Create Post");
		PostDto createPost1 = postService.createPost(createPost,file, userId, categoryId);
		return new ResponseEntity<>(createPost1, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageize) {
		log.info("Fetch Post by User");
		PostResponse posts = postService.getPostsByUSer(userId, pageNumber, pageize);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	// get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageize) {
		log.info("Fetch Post by Category");
		PostResponse posts = postService.getPostsByCategory(categoryId, pageNumber, pageize);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	// get all posts
	@GetMapping
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		log.info("Fetch All Post");
		PostResponse allPost = postService.getAllPost(pageNumber, pageize, sortBy, sortDir);
		return new ResponseEntity<>(allPost, HttpStatus.OK);
	}

	// get post details by id
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		log.info("Fetch Post by Post Id");
		PostDto postDto = postService.getPostById(postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	// delete post
	@DeleteMapping("/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		log.info("Delete Post");
		postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted !!", true);
	}

	// update post
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePodt(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		log.info("Update Post");
		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePost, HttpStatus.OK);
	}

}
