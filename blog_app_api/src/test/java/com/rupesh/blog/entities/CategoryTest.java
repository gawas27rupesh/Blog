package com.rupesh.blog.entities;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryTest {
	
	Category category=new Category();
	List<Post> posts;

	@Test
	void setter() {
		category.setCategoryId(1);
		category.setCategoryTitle("abc");
		category.setCategoryDescription("abc");
		category.setPosts(posts);
	}

	@Test
	void getter() {
		category.getCategoryId();
		category.getCategoryTitle();
		category.getCategoryDescription();
		category.getPosts();
	}

}
