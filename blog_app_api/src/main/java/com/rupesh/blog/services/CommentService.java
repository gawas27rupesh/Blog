package com.rupesh.blog.services;

import com.rupesh.blog.dto.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	String deleteComment(Integer commentId);
}
