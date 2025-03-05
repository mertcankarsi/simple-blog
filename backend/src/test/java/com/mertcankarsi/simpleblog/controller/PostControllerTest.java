package com.mertcankarsi.simpleblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mertcankarsi.simpleblog.dto.PostDto;
import com.mertcankarsi.simpleblog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostDto postDto;
    private String referenceKey;

    @BeforeEach
    void setUp() {
        referenceKey = UUID.randomUUID().toString();
        postDto = new PostDto();
        postDto.setReferenceKey(referenceKey);
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");
        postDto.setCreatedAt(LocalDateTime.now());
        postDto.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void getAllPosts_ShouldReturnListOfPosts() throws Exception {
        // Arrange
        List<PostDto> posts = Arrays.asList(postDto);
        when(postService.getAllPosts()).thenReturn(posts);

        // Act & Assert
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value(postDto.getTitle()))
                .andExpect(jsonPath("$[0].content").value(postDto.getContent()));
    }

    @Test
    void getPostByReferenceKey_ShouldReturnPost() throws Exception {
        // Arrange
        when(postService.getPostByReferenceKey(referenceKey)).thenReturn(postDto);

        // Act & Assert
        mockMvc.perform(get("/api/posts/{referenceKey}", referenceKey))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()));
    }

    @Test
    void createPost_ShouldCreateAndReturnPost() throws Exception {
        // Arrange
        when(postService.createPost(any(PostDto.class))).thenReturn(postDto);

        // Act & Assert
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()));
    }

    @Test
    void updatePost_ShouldUpdateAndReturnPost() throws Exception {
        // Arrange
        when(postService.updatePost(eq(referenceKey), any(PostDto.class))).thenReturn(postDto);

        // Act & Assert
        mockMvc.perform(put("/api/posts/{referenceKey}", referenceKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()));
    }

    @Test
    void deletePost_ShouldReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/posts/{referenceKey}", referenceKey))
                .andExpect(status().isNoContent());
    }

    @Test
    void createPost_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Arrange
        PostDto invalidPost = new PostDto();
        invalidPost.setTitle(""); // Empty title

        // Act & Assert
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPost)))
                .andExpect(status().isBadRequest());
    }
} 