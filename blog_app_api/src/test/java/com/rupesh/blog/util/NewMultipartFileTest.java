package com.rupesh.blog.util;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewMultipartFileTest {
	
	@InjectMocks
	NewMultipartFile newMultipartFile;

	@Test
	void getter() throws IOException {
		newMultipartFile.getName();
		newMultipartFile.getOriginalFilename();
		newMultipartFile.getContentType();
	}

}
