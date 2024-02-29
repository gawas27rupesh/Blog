package com.rupesh.blog.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ApiResponseTest {

	ApiResponse apiResponse = new ApiResponse();

	@Test
	void setter() {
		apiResponse.setMessage("abc");
		apiResponse.setSuccess(true);
	}

	@Test
	void getter() {
		apiResponse.getMessage();

	}

	@Test
    public void testApiResponseConstructor() {
        String message = "Test message";
        boolean success = true;
        
        ApiResponse response = new ApiResponse(message, success);
       
        assertEquals(message, response.getMessage());
        assertEquals(success, response.isSuccess());
    }

}
