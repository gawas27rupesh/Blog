package com.rupesh.blog.payloads;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
}
