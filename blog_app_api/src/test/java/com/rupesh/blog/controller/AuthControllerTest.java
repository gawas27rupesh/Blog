package com.rupesh.blog.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

	@Mock
	AuthController authController;
	
	@Test
	void authenticateTest() {
		assertNotNull(authController);
	}
	
}
