package com.mertcankarsi.simpleblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SimpleBlogApplicationTest {

  @Test
  void contextLoads() {
    // This test is intentionally left blank as it's a placeholder
  }

  @Test
  void applicationStarts() {
    SimpleBlogApplication.main(new String[] {});
  }
}
