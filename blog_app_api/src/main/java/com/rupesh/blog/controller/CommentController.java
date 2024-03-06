package com.rupesh.blog.controller;

import static com.rupesh.blog.enums.ApiKey.DATA;
import static com.rupesh.blog.enums.ApiKey.MESSAGE;
import static com.rupesh.blog.enums.ApiKey.SUCCESS;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupesh.blog.dto.CommentDto;
import com.rupesh.blog.enums.ApiKey;
import com.rupesh.blog.services.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/{postId}/comments")
	public ResponseEntity<EnumMap<ApiKey, Object>> createComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer postId) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			map.put(DATA, commentService.createComment(commentDto, postId));
			map.put(SUCCESS, true);
			log.info("Create Comment");
		} catch (Exception e) {
			log.error("Error Create Comment");
		}
		return ResponseEntity.ok(map);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<EnumMap<ApiKey, Object>> deleteComment(@PathVariable Integer commentId) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Delete Comment");
			map.put(MESSAGE, commentService.deleteComment(commentId));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.info("Delete Comment");
		}
		return ResponseEntity.ok(map);
	}
}
