package com.rupesh.blog.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer categoryId;
	@NotEmpty
	private String categoryTitle;
	@NotEmpty
	private String categoryDescription;
}
