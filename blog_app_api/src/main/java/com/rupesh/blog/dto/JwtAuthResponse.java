package com.rupesh.blog.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtAuthResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
}
