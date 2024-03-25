package com.rupesh.blog.serviceImpl;

import static com.rupesh.blog.evalMapper.CommentMapper.TO_COMMENT;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.rupesh.blog.dto.CommentDto;
import com.rupesh.blog.entities.Comment;
import com.rupesh.blog.entities.Post;
import com.rupesh.blog.exceptions.ResourceNotFoundException;
import com.rupesh.blog.repositories.CommentRepo;
import com.rupesh.blog.repositories.PostRepo;
import com.rupesh.blog.services.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	
	private final ModelMapper modelMapper;
	private final PostRepo postRepo;
	private final CommentRepo commentRepo;

	@Override
	public Optional<CommentDto> createComment(CommentDto commentDto, Integer postId) {
		log.info("Service Implementation");
		Post post =postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment comment =modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		return TO_COMMENT.apply(commentRepo.save(comment));
	}

	@Override
	public String deleteComment(Integer commentId) {
		log.info("Service Implementation");
		commentRepo.delete(commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId)));
		return "success";
	}
}
