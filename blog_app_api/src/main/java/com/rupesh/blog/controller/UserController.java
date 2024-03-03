package com.rupesh.blog.controller;

import static com.rupesh.blog.enums.ApiKey.DATA;
import static com.rupesh.blog.enums.ApiKey.MESSAGE;
import static com.rupesh.blog.enums.ApiKey.SUCCESS;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

import java.util.EnumMap;

import javax.validation.Valid;

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

import com.rupesh.blog.dto.UserDto;
import com.rupesh.blog.enums.ApiKey;
import com.rupesh.blog.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	@PostMapping("/")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<EnumMap<ApiKey, Object>> createUser(@Valid @RequestBody UserDto userDto) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Added User");
			map.put(DATA, userService.createUser(userDto));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.info("Added User");
		}
		//return created(fromCurrentContextPath().build().toUri()).body(map);
		return ResponseEntity.ok(map);
	}

	@PutMapping("/{userId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<EnumMap<ApiKey, Object>> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable("userId") Integer userId) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Update User");
			map.put(DATA, userService.updateUser(userDto, userId));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error("Exception Update User");
		}
		return ResponseEntity.ok(map);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<EnumMap<ApiKey, Object>> deleteUser(@PathVariable("userId") Integer uid) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Delete User");
			map.put(MESSAGE, userService.deleteUser(uid));
		} catch (Exception e) {
			log.error("Error Delete User");
		}
		return ResponseEntity.ok(map);
	}

	@GetMapping("/")
	@ResponseBody
	public ResponseEntity<EnumMap<ApiKey, Object>> getAllUsers() {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Fetch All User");
			map.put(DATA, userService.getAllUsers());
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.info("Fetch All User");
		}
		return ResponseEntity.ok(map);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<EnumMap<ApiKey, Object>> getSingleUser(@PathVariable("userId") Integer userId) {
		EnumMap<ApiKey, Object> map = new EnumMap<>(ApiKey.class);
		try {
			log.info("Fetch User");
			map.put(DATA, userService.getUserById(userId));
			map.put(SUCCESS, true);
		} catch (Exception e) {
			log.info("Fetch User");
		}
		return ResponseEntity.ok(map);
	}
}
