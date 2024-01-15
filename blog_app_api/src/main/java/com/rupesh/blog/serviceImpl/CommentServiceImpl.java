package com.rupesh.blog.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable("blogCache")
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		log.info("Service Implementation");
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment saveComment = this.commentRepo.save(comment);
		return this.modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	@CacheEvict("blogCache")
	public void deleteComment(Integer commentId) {
		log.info("Service Implementation");
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
		this.commentRepo.delete(comment);
	}
}
