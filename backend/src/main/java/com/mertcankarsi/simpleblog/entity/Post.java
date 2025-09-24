package com.mertcankarsi.simpleblog.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;

public class Post implements Serializable {

  @Id private Long id;
  @NotEmpty private String referenceKey;
  @NotEmpty private String title;
  @NotEmpty private String content;
  @NotNull private LocalDateTime createdAt;
  @NotNull private LocalDateTime updatedAt;

  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
    referenceKey = UUID.randomUUID().toString();
  }

  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
