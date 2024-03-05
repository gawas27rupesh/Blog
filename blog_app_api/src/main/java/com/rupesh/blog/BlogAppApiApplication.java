package com.rupesh.blog;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.rupesh.blog.constant.AppConstants;
import com.rupesh.blog.entities.Role;
import com.rupesh.blog.repositories.RoleRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

	private final RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role = new Role();
			role.setRoleId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setRoleId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = new ArrayList<>();
			roles.add(role);
			roles.add(role1);

			this.roleRepo.saveAll(roles);

		} catch (Exception e) {
			log.error("Error Occured.");
		}
	}
}
