package com.rupesh.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rupesh.blog.repositories.UserRepo;

@SpringBootTest
class BlogAppApiApplicationTests {
	
	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void repoTest() {
		String Classname = this.userRepo.getClass().getName();
		String packName= this.userRepo.getClass().getPackageName();
		System.out.println(Classname);
		System.out.println(packName);
	}

}
