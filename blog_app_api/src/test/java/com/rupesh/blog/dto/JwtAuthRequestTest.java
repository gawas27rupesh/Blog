package com.rupesh.blog.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtAuthRequestTest {
	
	JwtAuthRequest jwtAuthRequest=new JwtAuthRequest();

	@Test
	void setter() {
		jwtAuthRequest.setUsername("abc");
		jwtAuthRequest.setPassword("abc");
	}
	
	@Test
	void getter() {
		jwtAuthRequest.getUsername();
		jwtAuthRequest.getPassword();
	}
	
}
