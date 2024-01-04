package com.rupesh.blog.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupesh.blog.entities.Comment;
import com.rupesh.blog.entities.Post;
import com.rupesh.blog.exceptions.ResourceNotFoundException;
import com.rupesh.blog.payloads.CommentDto;
import com.rupesh.blog.repositories.CommentRepo;
import com.rupesh.blog.repositories.PostRepo;
import com.rupesh.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment saveComment = this.commentRepo.save(comment);
		return this.modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
		this.commentRepo.delete(comment);
	}
}
