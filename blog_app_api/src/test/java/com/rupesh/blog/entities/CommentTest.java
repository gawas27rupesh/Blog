package com.rupesh.blog.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentTest {
	
	Comment comment=new Comment();
	Post post;
	
	@Test
	void setter() {
		comment.setCommentId(1);
		comment.setContent("abc");
		comment.setPost(post);
	}
	
	@Test
	void getter() {
		comment.getCommentId();
		comment.getContent();
		comment.getPost();
	}

}
