package com.rupesh.blog.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppConstantsTest {

	@InjectMocks
	AppConstants appConstants;
	
	 @Test
	    public void testConstants() {
	        assertEquals("0", AppConstants.PAGE_NUMBER);
	        assertEquals("2", AppConstants.PAGE_SIZE);
	        assertEquals("postId", AppConstants.SORT_BY);
	        assertEquals("asc", AppConstants.SORT_DIR);
	        assertEquals(Integer.valueOf(502), AppConstants.NORMAL_USER);
	        assertEquals(Integer.valueOf(501), AppConstants.ADMIN_USER);
	    }

}
