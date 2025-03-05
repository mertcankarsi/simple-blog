package com.mertcankarsi.simpleblog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDto {
    private String referenceKey;
    
    @NotBlank(message = "Title cannot be empty")
    private String title;
    
    @NotBlank(message = "Content cannot be empty")
    private String content;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 