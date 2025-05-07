package com.mertcankarsi.simpleblog.repository;

import com.mertcankarsi.simpleblog.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  Optional<Post> findByReferenceKey(String referenceKey);

  void deleteByReferenceKey(String referenceKey);

  boolean existsByReferenceKey(String referenceKey);
}
