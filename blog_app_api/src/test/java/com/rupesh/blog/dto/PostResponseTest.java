package com.rupesh.blog.dto;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostResponseTest {
	
	PostResponse postResponse=new PostResponse();
	List<PostDto> content;

	@Test
	void setter() {
		postResponse.setContent(content);
		postResponse.setPageNumber(1);
		postResponse.setTotalElements(1);
		postResponse.setPageSize(1);
		postResponse.setTotalPages(1);
		postResponse.setLastPage(true);
	}
	
	@Test
	void getter() {
		postResponse.getContent();
		postResponse.getPageNumber();
		postResponse.getTotalElements();
		postResponse.getPageSize();
		postResponse.getTotalPages();
		
		postResponse.isLastPage();
	}
	
}
