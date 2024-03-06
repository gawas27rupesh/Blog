package com.rupesh.blog.serviceImpl;

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
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		log.info("Service Implementation");
		Post post =postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment comment =modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment saveComment =commentRepo.save(comment);
		return modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public String deleteComment(Integer commentId) {
		log.info("Service Implementation");
		Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
		commentRepo.delete(comment);
		return "success";
	}
}
