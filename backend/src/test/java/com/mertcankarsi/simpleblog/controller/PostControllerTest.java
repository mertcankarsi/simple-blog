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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
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
        postDto.setTitle("Test Post");
        postDto.setContent("Test Content");
        postDto.setReferenceKey(referenceKey);
        postDto.setCreatedAt(LocalDateTime.now());
        postDto.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void getAllPosts_ShouldReturnListOfPosts() throws Exception {
        when(postService.getAllPosts()).thenReturn(Arrays.asList(postDto));

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value(postDto.getTitle()))
                .andExpect(jsonPath("$[0].content").value(postDto.getContent()))
                .andExpect(jsonPath("$[0].referenceKey").value(postDto.getReferenceKey()));
    }

    @Test
    void getPostByReferenceKey_ShouldReturnPost() throws Exception {
        when(postService.getPostByReferenceKey(referenceKey)).thenReturn(postDto);

        mockMvc.perform(get("/api/posts/{referenceKey}", referenceKey))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()))
                .andExpect(jsonPath("$.referenceKey").value(postDto.getReferenceKey()));
    }

    @Test
    void createPost_ShouldReturnCreatedPost() throws Exception {
        when(postService.createPost(any(PostDto.class))).thenReturn(postDto);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()))
                .andExpect(jsonPath("$.referenceKey").value(postDto.getReferenceKey()));
    }

    @Test
    void updatePost_ShouldReturnUpdatedPost() throws Exception {
        when(postService.updatePost(eq(referenceKey), any(PostDto.class))).thenReturn(postDto);

        mockMvc.perform(put("/api/posts/{referenceKey}", referenceKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()))
                .andExpect(jsonPath("$.referenceKey").value(postDto.getReferenceKey()));
    }

    @Test
    void deletePost_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/posts/{referenceKey}", referenceKey))
                .andExpect(status().isNoContent());
    }

    @Test
    void createPost_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        PostDto invalidPostDto = new PostDto();
        invalidPostDto.setTitle(""); // Boş başlık

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPostDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePost_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        PostDto invalidPostDto = new PostDto();
        invalidPostDto.setTitle(""); // Boş başlık

        mockMvc.perform(put("/api/posts/{referenceKey}", referenceKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPostDto)))
                .andExpect(status().isBadRequest());
    }
} 