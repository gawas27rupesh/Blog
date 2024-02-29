package com.rupesh.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostTest {
	
	Post post=new Post();
	Set<Comment> comments=new HashSet<>();
	Category category;
	User user;
	Date addedDate;

	@Test
	void setter() {
		
		post.setPostId(1);
		post.setTitle("abc");
		post.setContent("abc");
		post.setImageName("abc");
		post.setAddedDate(addedDate);
		post.setCategory(category);
		post.setUser(user);
		post.setComments(comments);
		post.setObjectKey("abc");
	}
	
	@Test
	void getter() {
		post.getPostId();
		post.getTitle();
		post.getContent();
		post.getImageName();
		post.getAddedDate();
		post.getCategory();
		post.getUser();
		post.getComments();
		post.getObjectKey();
		
	}
	
}
