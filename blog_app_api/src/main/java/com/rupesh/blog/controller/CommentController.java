package com.rupesh.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupesh.blog.dto.ApiResponse;
import com.rupesh.blog.dto.CommentDto;
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
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {
		log.info("Create Comment");
		CommentDto createComment = commentService.createComment(commentDto, postId);
		return new ResponseEntity<>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		log.info("Delete Comment");
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully", true), HttpStatus.OK);
	}
}
