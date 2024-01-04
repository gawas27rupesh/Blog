package com.rupesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blog.entities.Category;
import com.rupesh.blog.payloads.CategoryDto;

public interface CategoryRepo extends JpaRepository<Category, Integer> {


}
