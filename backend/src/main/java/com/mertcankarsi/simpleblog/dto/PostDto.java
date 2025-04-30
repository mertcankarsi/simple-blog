package com.mertcankarsi.simpleblog.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class PostDto {

    private String referenceKey;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
