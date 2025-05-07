package com.mertcankarsi.simpleblog.service;

import com.mertcankarsi.simpleblog.dto.PostDto;
import com.mertcankarsi.simpleblog.dto.request.PostCreateDto;
import com.mertcankarsi.simpleblog.entity.Post;
import com.mertcankarsi.simpleblog.exception.PostNotFoundException;
import com.mertcankarsi.simpleblog.mapper.PostMapper;
import com.mertcankarsi.simpleblog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostMapper postMapper;
  private final PostRepository postRepository;

  public PostService(PostMapper postMapper, PostRepository postRepository) {
    this.postMapper = postMapper;
    this.postRepository = postRepository;
  }

  @Transactional(readOnly = true)
  public Page<PostDto> getAllPosts(Pageable pageable) {
    return postRepository.findAll(pageable).map(postMapper::toDto);
  }

  @Transactional(readOnly = true)
  public PostDto getPostByReferenceKey(String referenceKey) {
    return postRepository
        .findByReferenceKey(referenceKey)
        .map(postMapper::toDto)
        .orElseThrow(() -> new PostNotFoundException(referenceKey));
  }

  @Transactional
  public PostDto createPost(PostCreateDto postCreateDto) {
    Post post = postMapper.toEntity(postCreateDto);
    return postMapper.toDto(postRepository.save(post));
  }

  @Transactional
  public PostDto updatePost(String referenceKey, PostDto postDto) {
    Post existingPost =
        postRepository
            .findByReferenceKey(referenceKey)
            .orElseThrow(() -> new PostNotFoundException(referenceKey));

    postMapper.updateEntity(existingPost, postDto);
    return postMapper.toDto(postRepository.save(existingPost));
  }

  @Transactional
  public void deletePost(String referenceKey) {
    if (!postRepository.existsByReferenceKey(referenceKey)) {
      throw new PostNotFoundException(referenceKey);
    }
    postRepository.findByReferenceKey(referenceKey).ifPresent(postRepository::delete);
  }
}
