package com.rupesh.blog.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rupesh.blog.dto.CommentDto;
import com.rupesh.blog.services.CommentService;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @Test
    void createCommentTest() {
        CommentDto commentDto = new CommentDto();
        when(commentService.createComment(commentDto, 1)).thenReturn(commentDto);
        assertNotNull(commentController.createComment(commentDto, 1));
        when(commentService.createComment(commentDto, 1)).thenThrow(NullPointerException.class);
        assertNotNull(commentController.createComment(commentDto, 1));
   
    }

    @Test
    void deleteCommentTest() {
       when(commentService.deleteComment(1)).thenReturn("done");
       assertNotNull(commentController.deleteComment(1));
       when(commentService.deleteComment(1)).thenThrow(NullPointerException.class);
       assertNotNull(commentController.deleteComment(1));
    }
 
}
