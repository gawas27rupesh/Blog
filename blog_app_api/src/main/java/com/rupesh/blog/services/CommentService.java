package com.rupesh.blog.services;

import java.util.Optional;

import com.rupesh.blog.dto.CommentDto;

public interface CommentService {
	
	Optional<CommentDto> createComment(CommentDto commentDto,Integer postId);
	
	String deleteComment(Integer commentId);
}
