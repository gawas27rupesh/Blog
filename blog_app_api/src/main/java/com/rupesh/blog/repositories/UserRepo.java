package com.rupesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
