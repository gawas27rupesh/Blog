package com.rupesh.blog.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
}
