package com.mertcankarsi.simpleblog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.mertcankarsi.simpleblog.dto.PostDto;
import com.mertcankarsi.simpleblog.dto.request.PostCreateDto;
import com.mertcankarsi.simpleblog.service.PostService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

  @Mock private PostService postService;

  @InjectMocks private PostController postController;

  private PostDto postDto;
  private PostCreateDto postCreateDto;
  private String referenceKey;

  @BeforeEach
  void setUp() {
    referenceKey = "test-reference-key";
    postDto = new PostDto();
    postDto.setTitle("Test Title");
    postDto.setContent("Test Content");
    postDto.setReferenceKey(referenceKey);

    postCreateDto = new PostCreateDto("Test Title", "Test Content");
  }

  @Test
  void getAllPosts_ShouldReturnListOfPosts() {
    // Arrange
    List<PostDto> postList = Collections.singletonList(postDto);
    Page<PostDto> expectedPosts = new PageImpl<>(postList, PageRequest.of(0, 10), postList.size());
    when(postService.getAllPosts(any(PageRequest.class))).thenReturn(expectedPosts);

    // Act
    ResponseEntity<Page<PostDto>> response = postController.getAllPosts(PageRequest.of(0, 10));

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(expectedPosts, response.getBody());
    verify(postService).getAllPosts(any(PageRequest.class));
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
    when(postService.createPost(any(PostCreateDto.class))).thenReturn(postDto);

    // Act
    ResponseEntity<PostDto> response = postController.createPost(postCreateDto);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(postDto, response.getBody());
    verify(postService).createPost(any(PostCreateDto.class));
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
