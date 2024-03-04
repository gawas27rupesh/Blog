package com.rupesh.blog.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rupesh.blog.aws.AmazonClient;

@ExtendWith(MockitoExtension.class)
class AwsRestControllerTest {
	
	@InjectMocks
	AwsRestController awsRestController;
	
	@Mock
	AmazonClient amazonClient;

	@Test
	void uploadImageToS3() {
		assertNotNull(awsRestController.uploadImageToS3(null));
	}
	
	@Test
	void getImageFromS3Test() {
		assertNotNull(awsRestController.getImageFromS3("abc"));
	}
	
	@Test
	void deleteImageFromS3Test() {
		assertNotNull(awsRestController.deleteImageFromS3("abc"));
	}
	
	@Test
	void getAllImageFromS3Test() {
		assertNotNull(awsRestController.getAllImageFromS3());
	}

}
