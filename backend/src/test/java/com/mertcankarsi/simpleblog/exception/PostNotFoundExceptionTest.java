package com.mertcankarsi.simpleblog.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PostNotFoundExceptionTest {

  @Test
  void constructor_ShouldSetMessage() {
    // Arrange
    String referenceKey = "test-reference-key";
    String expectedMessage = "Post not found with reference key: " + referenceKey;

    // Act
    PostNotFoundException exception = new PostNotFoundException(referenceKey);

    // Assert
    assertEquals(expectedMessage, exception.getMessage());
  }
}
