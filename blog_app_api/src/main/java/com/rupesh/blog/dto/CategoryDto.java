package com.rupesh.blog.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	private Integer categoryId;
	@NotEmpty
	private String categoryTitle;
	@NotEmpty
	private String categoryDescription;
}
