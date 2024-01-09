package com.rupesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
