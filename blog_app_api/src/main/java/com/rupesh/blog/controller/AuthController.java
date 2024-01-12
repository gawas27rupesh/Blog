package com.rupesh.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupesh.blog.dto.JwtAuthRequest;
import com.rupesh.blog.dto.JwtAuthResponse;
import com.rupesh.blog.dto.UserDto;
import com.rupesh.blog.exceptions.ApiException;
import com.rupesh.blog.security.JwtTokenHelper;
import com.rupesh.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private static UserDetailsService userDetailsService;
	private static AuthenticationManager authenticationManager;
	private static JwtTokenHelper jwtTokenHelper;
	private static UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			authenticationManager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			throw new ApiException(" Invalid Username or Password...!!!");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		UserDto registerNewUser = userService.registerNewUser(userDto);
		return new ResponseEntity<>(registerNewUser, HttpStatus.CREATED);
	}
}
