package com.rupesh.blog.controller;

import static com.rupesh.blog.enums.ApiKey.DATA;
import static com.rupesh.blog.enums.ApiKey.MESSAGE;
import static com.rupesh.blog.enums.ApiKey.SUCCESS;

import java.io.IOException;
import java.util.EnumMap;

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
import com.rupesh.blog.constant.AppConstants;
import com.rupesh.blog.dto.PostDto;
import com.rupesh.blog.enums.ApiKey;
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

	@PostMapping(value = "/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<EnumMap<ApiKey, Object>> createPost(@RequestParam("image") MultipartFile file,
			@RequestParam("userData") String userData, @PathVariable Integer userId, @PathVariable Integer categoryId)
			throws IOException {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Create Post");
			PostDto createPost = mapper.readValue(userData, PostDto.class);
			map.put(DATA, postService.createPost(createPost, file, userId, categoryId));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Error Create Post");
		}
		return ResponseEntity.ok(map);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<EnumMap<ApiKey, Object>> getPostByUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageize) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Fetch Post by User");
			map.put(DATA, postService.getPostsByUSer(userId, pageNumber, pageize));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Error Fetch Post by User");
		}
		return ResponseEntity.ok(map);
	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<EnumMap<ApiKey, Object>> getPostByCategory(@PathVariable Integer categoryId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageize) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Fetch Post by Category");
			map.put(DATA, postService.getPostsByCategory(categoryId, pageNumber, pageize));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Error Fetch Post by Category");
		}
		return ResponseEntity.ok(map);
	}

	@GetMapping
	public ResponseEntity<EnumMap<ApiKey, Object>> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Fetch All Post");
			map.put(DATA, postService.getAllPost(pageNumber, pageize, sortBy, sortDir));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Error Fetch All Post");
		}
		return ResponseEntity.ok(map);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<EnumMap<ApiKey, Object>> getPostById(@PathVariable Integer postId) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Fetch Post by Post Id");
			map.put(DATA, postService.getPostById(postId));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.info("Fetch Post by Post Id");
		}
		return ResponseEntity.ok(map);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<EnumMap<ApiKey, Object>> deletePost(@PathVariable Integer postId) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Delete Post");
			;
			map.put(MESSAGE,postService.deletePost(postId));
		} catch (Exception e) {
			log.error("Error Delete Post");
		}
		return ResponseEntity.ok(map);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<EnumMap<ApiKey, Object>> updatePodt(@RequestBody PostDto postDto,
			@PathVariable Integer postId) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Update Post");
			map.put(DATA, postService.updatePost(postDto, postId));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Error Update Post");
		}
		return ResponseEntity.ok(map);
	}

}
