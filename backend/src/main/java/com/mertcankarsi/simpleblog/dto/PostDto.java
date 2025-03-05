package com.mertcankarsi.simpleblog.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDto {
    private String referenceKey;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 