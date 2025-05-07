package com.mertcankarsi.simpleblog.exception;

public class PostNotFoundException extends RuntimeException {

  private final String referenceKey;

  public PostNotFoundException(String referenceKey) {
    super("Post not found with reference key: " + referenceKey);
    this.referenceKey = referenceKey;
  }

  public String getReferenceKey() {
    return referenceKey;
  }
}
