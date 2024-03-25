package com.rupesh.blog.evalMapper;

import static com.rupesh.blog.util.FunctionUtil.evalMapper;

import java.util.Optional;
import java.util.function.Function;

import com.rupesh.blog.dto.CommentDto;
import com.rupesh.blog.entities.Comment;

public class CommentMapper {

	private CommentMapper() {
		
	}
	
	public static final Function<Comment, Optional<CommentDto>> TO_COMMENT = e -> evalMapper(e,
			CommentDto.class);
	
}
