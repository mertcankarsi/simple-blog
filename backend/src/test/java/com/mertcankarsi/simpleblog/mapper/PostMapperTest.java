package com.mertcankarsi.simpleblog.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.mertcankarsi.simpleblog.dto.PostDto;
import com.mertcankarsi.simpleblog.dto.request.PostCreateDto;
import com.mertcankarsi.simpleblog.entity.Post;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostMapperTest {

  @Autowired private PostMapper postMapper;

  private Post post;
  private PostDto postDto;
  private PostCreateDto postCreateDto;
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
    postDto.setCreatedAt(LocalDateTime.now());
    postDto.setUpdatedAt(LocalDateTime.now());

    postCreateDto = new PostCreateDto("Test Title", "Test Content");
  }

  @Test
  void toEntity_ShouldMapDtoToEntity() {
    // Act
    Post result = postMapper.toEntity(postCreateDto);

    // Assert
    assertNotNull(result);
    assertNull(result.getReferenceKey());
    assertNull(result.getCreatedAt());
    assertNull(result.getUpdatedAt());
    assertEquals(postDto.getTitle(), result.getTitle());
    assertEquals(postDto.getContent(), result.getContent());
  }

  @Test
  void toDto_ShouldMapEntityToDto() {
    // Act
    PostDto result = postMapper.toDto(post);

    // Assert
    assertNotNull(result);
    assertEquals(post.getReferenceKey(), result.getReferenceKey());
    assertEquals(post.getTitle(), result.getTitle());
    assertEquals(post.getContent(), result.getContent());
    assertEquals(post.getCreatedAt(), result.getCreatedAt());
    assertEquals(post.getUpdatedAt(), result.getUpdatedAt());
  }

  @Test
  void toDtoList_ShouldMapEntityListToDtoList() {
    // Arrange
    List<Post> posts = Arrays.asList(post);

    // Act
    List<PostDto> result = postMapper.toDtoList(posts);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(post.getReferenceKey(), result.get(0).getReferenceKey());
    assertEquals(post.getTitle(), result.get(0).getTitle());
    assertEquals(post.getContent(), result.get(0).getContent());
    assertEquals(post.getCreatedAt(), result.get(0).getCreatedAt());
    assertEquals(post.getUpdatedAt(), result.get(0).getUpdatedAt());
  }

  @Test
  void updateEntity_ShouldUpdateEntityWithDtoValues() {
    // Arrange
    Post existingPost = new Post();
    existingPost.setReferenceKey("old-key");
    existingPost.setTitle("Old Title");
    existingPost.setContent("Old Content");
    existingPost.setCreatedAt(LocalDateTime.now().minusDays(1));
    existingPost.setUpdatedAt(LocalDateTime.now().minusDays(1));

    PostDto updateDto = new PostDto();
    updateDto.setTitle("New Title");
    updateDto.setContent("New Content");

    // Act
    postMapper.updateEntity(existingPost, updateDto);

    // Assert
    assertEquals("old-key", existingPost.getReferenceKey());
    assertNotNull(existingPost.getCreatedAt());
    assertNotNull(existingPost.getUpdatedAt());
    assertEquals(updateDto.getTitle(), existingPost.getTitle());
    assertEquals(updateDto.getContent(), existingPost.getContent());
  }

  @Test
  void toEntity_WhenDtoIsNull_ShouldReturnNull() {
    // Act
    Post result = postMapper.toEntity(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toDto_WhenEntityIsNull_ShouldReturnNull() {
    // Act
    PostDto result = postMapper.toDto(null);

    // Assert
    assertNull(result);
  }

  @Test
  void toDtoList_WhenEntityListIsNull_ShouldReturnNull() {
    // Act
    List<PostDto> result = postMapper.toDtoList(null);

    // Assert
    assertNull(result);
  }

  @Test
  void updateEntity_WhenDtoIsNull_ShouldNotUpdateEntity() {
    // Arrange
    Post existingPost = new Post();
    existingPost.setTitle("Old Title");
    existingPost.setContent("Old Content");

    // Act
    postMapper.updateEntity(existingPost, null);

    // Assert
    assertEquals("Old Title", existingPost.getTitle());
    assertEquals("Old Content", existingPost.getContent());
  }
}
