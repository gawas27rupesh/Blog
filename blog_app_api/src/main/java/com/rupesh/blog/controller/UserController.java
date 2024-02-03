package com.rupesh.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rupesh.blog.dto.ApiResponse;
import com.rupesh.blog.dto.UserDto;
import com.rupesh.blog.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	// POST-create user
	@PostMapping("/")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		log.info("Added User");
		UserDto creUserDto = userService.createUser(userDto);
		return new ResponseEntity<>(creUserDto, HttpStatus.CREATED);
	}

	// PUT- update user//path uri variable
	@PutMapping("/{userId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable("userId") Integer userId) {
		log.info("Update User");
		UserDto updateUser = userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updateUser);
	}

	// only ADMIN can delete
	// DELETE -delete user
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
		log.info("Delete User");
		userService.deleteUser(uid);
		return new ResponseEntity<>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}

	// GET- All user get
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<UserDto>> getAllUsers() {
		log.info("Fetch All User");
		return ResponseEntity.ok(userService.getAllUsers());
	}

	// GET- user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId) {
		log.info("Fetch User");
		return ResponseEntity.ok(userService.getUserById(userId));
	}
}
