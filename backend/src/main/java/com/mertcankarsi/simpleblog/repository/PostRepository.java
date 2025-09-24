package com.mertcankarsi.simpleblog.repository;

import com.mertcankarsi.simpleblog.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

public interface PostRepository extends Repository<Post, Long> {

  @Query("select * from posts order by id desc")
  List<Post> findAll() throws DataAccessException;

  @Query("select count(*) from posts")
  int count() throws DataAccessException;

  Optional<Post> findByReferenceKey(String referenceKey);

  Post save(Post post);

  void deleteByReferenceKey(String referenceKey);

  boolean existsByReferenceKey(String referenceKey);

  void delete(Post post);
}
