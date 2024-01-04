package com.rupesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
