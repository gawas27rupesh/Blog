package com.rupesh.blog.config;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

import com.rupesh.blog.configContent.ContentConfig;

@ExtendWith(MockitoExtension.class)
class ContentConfigTest {
	
	@InjectMocks
	ContentConfig contentConfig;
	
	@Mock
	ContentNegotiationConfigurer configurerMock;
	
	
	
	@Test
    void configureContentNegotiation_Test() {
    }

}
