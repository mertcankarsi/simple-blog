package com.mertcankarsi.simpleblog.repository;

import com.mertcankarsi.simpleblog.entity.User;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
  Optional<User> findByEmail(String email);
}
