package com.rupesh.blog.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleTest {
	
	Role role=new Role();
	
	@Test
	void setter() {
		role.setRoleId(1);
		role.setName("abc");
	}
	
	@Test
	void getter() {
		role.getRoleId();
		role.getName();
	}

}
