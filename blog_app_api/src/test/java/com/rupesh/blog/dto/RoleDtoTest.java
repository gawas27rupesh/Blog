package com.rupesh.blog.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleDtoTest {

	RoleDto roleDto=new RoleDto();
	
	@Test
	void setter() {
		roleDto.setId(1);
		roleDto.setName("abc");
	}
	
	@Test
	void getter() {
		roleDto.getId();
		roleDto.getName();
	}
	
}
