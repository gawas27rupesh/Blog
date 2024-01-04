package com.rupesh.blog.payloads;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.rupesh.blog.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<>();
	
	
}
