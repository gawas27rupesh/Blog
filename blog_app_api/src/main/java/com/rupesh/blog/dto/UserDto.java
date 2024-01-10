package com.rupesh.blog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	@NotEmpty
	@Size(min = 4,message = "Username must be min of 4 characters.")
	private String name;
	
	@Email(message = "Your Email address is invalided.")
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Size(min = 4,max = 10,message = "Password must be min of 3 chars and max of 10 chars,")
	//@Pattern(regexp = )
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();
	
}
