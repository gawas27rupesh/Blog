package com.rupesh.blog.evalMapper;

import static com.rupesh.blog.util.FunctionUtil.evalMapper;

import java.util.Optional;
import java.util.function.Function;

import com.rupesh.blog.dto.UserDto;
import com.rupesh.blog.entities.User;

public class UserMapper {
	
	private UserMapper() {

	}
	
	public static final Function<User, Optional<UserDto>> TO_USER = e -> evalMapper(e,
			UserDto.class);
	
}
