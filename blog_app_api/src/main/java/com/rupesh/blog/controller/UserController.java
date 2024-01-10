package com.rupesh.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
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

import com.rupesh.blog.payloads.ApiResponse;
import com.rupesh.blog.payloads.UserDto;
import com.rupesh.blog.services.UserService;

@RestController
@EnableCaching
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	//POST-create user
	@PostMapping("/")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto creUserDto=this.userService.createUser(userDto);
		return new ResponseEntity<>(creUserDto,HttpStatus.CREATED);		
	}
	
	//PUT- update user//path uri variable
	@PutMapping("/{userId}")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId) {
		 UserDto updateUser = this.userService.updateUser(userDto, userId);	
		 return ResponseEntity.ok(updateUser);
	}
	
	//only ADMIN can delete
	//DELETE -delete user
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
		 this.userService.deleteUser(uid);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
	}
	
	//GET- All user get
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<List<UserDto>> getAllUsers() {
		System.out.println("Controller");
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	//GET- user get
	@GetMapping("/{userId}")
	//@Cacheable(value="CacheUser",key="#userId")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId) {
		System.out.println("Controller");
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
}

	
