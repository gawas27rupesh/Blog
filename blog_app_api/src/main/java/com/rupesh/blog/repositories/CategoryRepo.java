package com.rupesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
