
package com.rupesh.blog.serviceImpl;

import static com.rupesh.blog.evalMapper.UserMapper.TO_USER;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rupesh.blog.constant.AppConstants;
import com.rupesh.blog.dto.UserDto;
import com.rupesh.blog.entities.Role;
import com.rupesh.blog.entities.User;
import com.rupesh.blog.exceptions.ResourceNotFoundException;
import com.rupesh.blog.repositories.RoleRepo;
import com.rupesh.blog.repositories.UserRepo;
import com.rupesh.blog.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepo userRepo;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		log.info("Service Implementation");
		User map = modelMapper.map(userDto, User.class);
		map.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		User savedUser = userRepo.save(map);
		return this.modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		log.info("Service Implementation");
		User map = modelMapper.map(userDto, User.class);
		map.setUserId(userId);
		User updateUser = this.userRepo.save(map);
		return this.modelMapper.map(updateUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		log.info("Service Implementation");
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<Optional<UserDto>> getAllUsers() {
		log.info("Service Implementation");
		List<User> users = this.userRepo.findAll();
		//List<UserDto> userDto = users.stream().map(u->modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
		return users.stream().map(u->TO_USER.apply(u)).collect(Collectors.toList());
	}

	@Override
	public String deleteUser(Integer userId) {
		log.info("Service Implementation");
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
		return "success";
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		log.info("Service Implementation");
		User user = this.modelMapper.map(userDto, User.class);

		// encoded password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
}
