package com.rupesh.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blog.entities.Category;
import com.rupesh.blog.entities.Post;
import com.rupesh.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	Page<Post> findByCategory(Category category, Pageable p);
	Page<Post> findByUser(User user, Pageable p);
	List<Post> findByTitleContaining(String title);

}
