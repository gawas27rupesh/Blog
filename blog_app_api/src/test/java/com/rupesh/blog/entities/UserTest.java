package com.rupesh.blog.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserTest {
	
	User user=new User();
	List<Post> posts=new ArrayList<>();
	Set<Role> roles=new HashSet<>();
	
	@Test
	void setter() {
		
		user.setUserId(1);
		user.setName("abc");
		user.setEmail("abc");
		user.setPassword("abc");
		user.setAbout("abc");
		user.setPosts(posts);
		user.setRoles(roles);
	}
	
	@Test
	void getter() {
		user.getUserId();
		user.getName();
		user.getEmail();
		user.getPassword();
		user.getAbout();
		user.getPosts();
		user.getRoles();
		user.getUsername();
		
		user.isAccountNonExpired();
		user.isAccountNonLocked();
		user.isCredentialsNonExpired();
		user.isEnabled();
		
		user.getAuthorities();
		
		
	}

}
