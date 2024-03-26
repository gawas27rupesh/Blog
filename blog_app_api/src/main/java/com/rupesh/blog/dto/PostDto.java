package com.rupesh.blog.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDto {
	
	private Integer postId;
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	private String imageName;
	private byte[] image;
	private String objectKey;
	private String addedDate;
	private CategoryDto category;
	private UserDto user;
	private Set<CommentDto> comments = new HashSet<>();

}
