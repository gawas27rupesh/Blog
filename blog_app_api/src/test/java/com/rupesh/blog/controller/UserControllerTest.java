package com.rupesh.blog.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rupesh.blog.dto.UserDto;
import com.rupesh.blog.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	
	@Test
	void createUserTest() {
		UserDto userDto=mock(UserDto.class);
		when(userService.createUser(userDto)).thenReturn(userDto);
		assertNotNull(userController.createUser(userDto));
		when(userService.createUser(userDto)).thenThrow(NullPointerException.class);
		assertNotNull(userController.createUser(userDto));
	}
	
	@Test
	void updateUserTest() {
		UserDto userDto=mock(UserDto.class);
		when(userService.updateUser(userDto,1)).thenReturn(userDto);
		assertNotNull(userController.updateUser(userDto,1));
		when(userService.updateUser(userDto,1)).thenThrow(NullPointerException.class);
		assertNotNull(userController.updateUser(userDto,1));
	}
	
	@Test
	void deleteUserTest() {
		when(userService.deleteUser(1)).thenReturn("done");
		assertNotNull(userController.deleteUser(1));
		when(userService.deleteUser(1)).thenThrow(NullPointerException.class);
		assertNotNull(userController.deleteUser(1));
	}

	@Test
	void getAllUsersTest() {
	}
	
	@Test
	void getSingleUserTest() {
		UserDto userDto=mock(UserDto.class);
		when(userService.getUserById(1)).thenReturn(userDto);
		assertNotNull(userController.getSingleUser(1));
		when(userService.getUserById(1)).thenThrow(NullPointerException.class);
		assertNotNull(userController.getSingleUser(1));
	}

}
