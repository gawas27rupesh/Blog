package com.rupesh.blog.services;

import java.util.List;
import java.util.Optional;

import com.rupesh.blog.dto.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<Optional<UserDto>> getAllUsers();
	
	String deleteUser(Integer userId);

}
