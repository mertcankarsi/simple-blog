package com.mertcankarsi.simpleblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class SimpleBlogApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    void applicationStarts() {
        SimpleBlogApplication.main(new String[]{});
    }
} 