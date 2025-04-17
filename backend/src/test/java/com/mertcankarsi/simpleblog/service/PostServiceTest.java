package com.mertcankarsi.simpleblog.service;

import com.mertcankarsi.simpleblog.dto.PostDto;
import com.mertcankarsi.simpleblog.entity.Post;
import com.mertcankarsi.simpleblog.exception.PostNotFoundException;
import com.mertcankarsi.simpleblog.mapper.PostMapper;
import com.mertcankarsi.simpleblog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    private Post post;
    private PostDto postDto;
    private String referenceKey;

    @BeforeEach
    void setUp() {
        referenceKey = UUID.randomUUID().toString();

        post = new Post();
        post.setReferenceKey(referenceKey);
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postDto = new PostDto();
        postDto.setReferenceKey(referenceKey);
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());
    }

    @Test
    void getAllPosts_ShouldReturnListOfPosts() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(List.of(post), pageable, 1);
        when(postRepository.findAll(pageable)).thenReturn(postPage);
        when(postMapper.toDto(any(Post.class))).thenReturn(postDto);

        // Act
        Page<PostDto> result = postService.getAllPosts(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(postDto, result.getContent().get(0));
        verify(postRepository).findAll(pageable);
        verify(postMapper).toDto(any(Post.class));
    }

    @Test
    void getPostByReferenceKey_WhenPostExists_ShouldReturnPost() {
        // Arrange
        when(postRepository.findByReferenceKey(any())).thenReturn(Optional.of(post));
        when(postMapper.toDto(any())).thenReturn(postDto);

        // Act
        PostDto result = postService.getPostByReferenceKey(referenceKey);

        // Assert
        assertNotNull(result);
        assertAll(
                () -> assertEquals(postDto.getReferenceKey(), result.getReferenceKey()),
                () -> assertEquals(postDto.getTitle(), result.getTitle()),
                () -> assertEquals(postDto.getContent(), result.getContent()));
        verify(postRepository).findByReferenceKey(any());
        verify(postMapper).toDto(any());
    }

    @Test
    void getPostByReferenceKey_WhenPostDoesNotExist_ShouldThrowException() {
        // Arrange
        when(postRepository.findByReferenceKey(referenceKey)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PostNotFoundException.class, () -> postService.getPostByReferenceKey(referenceKey));
        verify(postRepository).findByReferenceKey(referenceKey);
        verify(postMapper, never()).toDto(any());
    }

    @Test
    void createPost_ShouldReturnCreatedPost() {
        // Arrange
        when(postMapper.toEntity(postDto)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postDto);

        // Act
        PostDto result = postService.createPost(postDto);

        // Assert
        assertNotNull(result);
        assertEquals(postDto, result);
        verify(postMapper).toEntity(postDto);
        verify(postRepository).save(post);
        verify(postMapper).toDto(post);
    }

    @Test
    void updatePost_WhenPostExists_ShouldReturnUpdatedPost() {
        // Arrange
        when(postRepository.findByReferenceKey(any())).thenReturn(Optional.of(post));
        when(postRepository.save(any())).thenReturn(post);
        when(postMapper.toDto(any())).thenReturn(postDto);

        // Act
        PostDto result = postService.updatePost(referenceKey, postDto);

        // Assert
        assertNotNull(result);
        assertEquals(postDto, result);
        verify(postRepository).findByReferenceKey(referenceKey);
        verify(postMapper).updateEntity(any(Post.class), any(PostDto.class));
        verify(postRepository).save(any());
        verify(postMapper).toDto(any());
    }

    @Test
    void updatePost_WhenPostDoesNotExist_ShouldThrowException() {
        // Arrange
        when(postRepository.findByReferenceKey(referenceKey)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PostNotFoundException.class, () -> postService.updatePost(referenceKey, postDto));
        verify(postRepository).findByReferenceKey(referenceKey);
        verify(postMapper, never()).updateEntity(any(), any());
        verify(postRepository, never()).save(any());
        verify(postMapper, never()).toDto(any());
    }

    @Test
    void deletePost_WhenPostExists_ShouldDeletePost() {
        // Arrange
        when(postRepository.existsByReferenceKey(referenceKey)).thenReturn(true);
        when(postRepository.findByReferenceKey(referenceKey)).thenReturn(Optional.of(post));

        // Act
        postService.deletePost(referenceKey);

        // Assert
        verify(postRepository).existsByReferenceKey(referenceKey);
        verify(postRepository).findByReferenceKey(referenceKey);
        verify(postRepository).delete(post);
    }

    @Test
    void deletePost_WhenPostDoesNotExist_ShouldThrowException() {
        // Arrange
        when(postRepository.existsByReferenceKey(referenceKey)).thenReturn(false);

        // Act & Assert
        assertThrows(PostNotFoundException.class, () -> postService.deletePost(referenceKey));
        verify(postRepository).existsByReferenceKey(referenceKey);
        verify(postRepository, never()).findByReferenceKey(any());
        verify(postRepository, never()).delete(any());
    }
}
