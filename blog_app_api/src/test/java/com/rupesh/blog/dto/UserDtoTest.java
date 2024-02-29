package com.rupesh.blog.dto;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDtoTest {

	UserDto userDto=new UserDto();
	Set<RoleDto> roles=new HashSet<>();
	
	@Test
	void setter() {
		userDto.setId(1);
		userDto.setAbout("abc");
		userDto.setPassword("abc");
		userDto.setEmail("abc");
		userDto.setName("abc");
		userDto.setRoles(roles);
	}
	
	@Test
	void getter() {
		userDto.getId();
		userDto.getAbout();
		userDto.getPassword();
		userDto.getEmail();
		userDto.getName();
		userDto.getRoles();
	}
	
}
