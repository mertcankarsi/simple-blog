package com.mertcankarsi.simpleblog.controller;

import com.mertcankarsi.simpleblog.dto.PostDto;
import com.mertcankarsi.simpleblog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{referenceKey}")
    public ResponseEntity<PostDto> getPostByReferenceKey(@PathVariable String referenceKey) {
        return ResponseEntity.ok(postService.getPostByReferenceKey(referenceKey));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @PutMapping("/{referenceKey}")
    public ResponseEntity<PostDto> updatePost(@PathVariable String referenceKey, @Valid @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(referenceKey, postDto));
    }

    @DeleteMapping("/{referenceKey}")
    public ResponseEntity<Void> deletePost(@PathVariable String referenceKey) {
        postService.deletePost(referenceKey);
        return ResponseEntity.noContent().build();
    }
} 