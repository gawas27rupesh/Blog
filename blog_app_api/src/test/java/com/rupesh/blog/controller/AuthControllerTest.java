package com.rupesh.blog.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rupesh.blog.dto.JwtAuthRequest;
import com.rupesh.blog.dto.UserDto;
import com.rupesh.blog.services.UserService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

	@InjectMocks
	AuthController authController;
	
	@Mock
	UserService userService;
	
	@Test
	void createTokenTest() {
		JwtAuthRequest jwtAuthRequest=mock(JwtAuthRequest.class);
		
	}
	
	@Test
	void registerUserTest() {
		UserDto userDto=mock(UserDto.class);
		when(userService.registerNewUser(userDto)).thenReturn(userDto);
		assertNotNull(authController.registerUser(userDto));
	}
	
}
