package com.mertcankarsi.simpleblog.repository;

import com.mertcankarsi.simpleblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    Optional<Post> findByReferenceKey(String referenceKey);

    void deleteByReferenceKey(String referenceKey);

    boolean existsByReferenceKey(String referenceKey);
} 
