package com.mertcankarsi.simpleblog.controller;

import com.mertcankarsi.simpleblog.dto.PostDto;
import com.mertcankarsi.simpleblog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private PostDto postDto;
    private String referenceKey;

    @BeforeEach
    void setUp() {
        referenceKey = "test-reference-key";
        postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");
        postDto.setReferenceKey(referenceKey);
    }

    @Test
    void getAllPosts_ShouldReturnListOfPosts() {
        // Arrange
        List<PostDto> expectedPosts = Arrays.asList(postDto);
        when(postService.getAllPosts()).thenReturn(expectedPosts);

        // Act
        ResponseEntity<List<PostDto>> response = postController.getAllPosts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPosts, response.getBody());
        verify(postService).getAllPosts();
    }

    @Test
    void getPostByReferenceKey_ShouldReturnPost() {
        // Arrange
        when(postService.getPostByReferenceKey(referenceKey)).thenReturn(postDto);

        // Act
        ResponseEntity<PostDto> response = postController.getPostByReferenceKey(referenceKey);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postDto, response.getBody());
        verify(postService).getPostByReferenceKey(referenceKey);
    }

    @Test
    void createPost_ShouldReturnCreatedPost() {
        // Arrange
        when(postService.createPost(any(PostDto.class))).thenReturn(postDto);

        // Act
        ResponseEntity<PostDto> response = postController.createPost(postDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(postDto, response.getBody());
        verify(postService).createPost(postDto);
    }

    @Test
    void updatePost_ShouldReturnUpdatedPost() {
        // Arrange
        when(postService.updatePost(anyString(), any(PostDto.class))).thenReturn(postDto);

        // Act
        ResponseEntity<PostDto> response = postController.updatePost(referenceKey, postDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postDto, response.getBody());
        verify(postService).updatePost(referenceKey, postDto);
    }

    @Test
    void deletePost_ShouldReturnNoContent() {
        // Act
        ResponseEntity<Void> response = postController.deletePost(referenceKey);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(postService).deletePost(referenceKey);
    }
} 