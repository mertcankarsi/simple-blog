package com.mertcankarsi.simpleblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SimpleBlogApplicationTests {

    @Test
    void contextLoads() {
        // Spring context'in başarıyla yüklendiğini test eder
    }

    @Test
    void applicationStarts() {
        // Uygulamanın başarıyla başlatıldığını test eder
        SimpleBlogApplication.main(new String[]{});
    }
} 