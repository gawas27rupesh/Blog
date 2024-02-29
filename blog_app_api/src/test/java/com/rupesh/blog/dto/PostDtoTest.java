package com.rupesh.blog.dto;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostDtoTest {
	
	PostDto postDto=new PostDto();
	Set<CommentDto> comments=new HashSet<>();
	CategoryDto category;
	UserDto user;
	byte[] image;

	@Test
	void setter() {
		postDto.setPostId(1);
		postDto.setTitle("abc");
		postDto.setContent("abc");
		postDto.setImageName("abc");
		postDto.setImage(image);
		postDto.setObjectKey("abc");
		postDto.setAddedDate("abc");
		postDto.setCategory(category);
		postDto.setUser(user);
		postDto.setComments(comments);
	}
	
	@Test
	void getter() {
		postDto.getPostId();
		postDto.getTitle();
		postDto.getContent();
		postDto.getImageName();
		postDto.getImage();
		postDto.getObjectKey();
		postDto.getAddedDate();
		postDto.getCategory();
		postDto.getUser();
		postDto.getComments();
		
	}
	
}
