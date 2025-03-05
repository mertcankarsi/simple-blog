package com.mertcankarsi.simpleblog.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handlePostNotFoundException_ShouldReturnNotFoundResponse() {
        // Arrange
        String referenceKey = "test-key";
        PostNotFoundException exception = new PostNotFoundException(referenceKey);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handlePostNotFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().get("status"));
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), response.getBody().get("error"));
        assertEquals("Post not found with reference key: " + referenceKey, response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
        assertTrue(response.getBody().get("timestamp") instanceof LocalDateTime);
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerErrorResponse() {
        // Arrange
        String errorMessage = "Test error message";
        Exception exception = new RuntimeException(errorMessage);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGenericException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().get("status"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response.getBody().get("error"));
        assertEquals("An unexpected error occurred", response.getBody().get("message"));
        assertNotNull(response.getBody().get("timestamp"));
        assertTrue(response.getBody().get("timestamp") instanceof LocalDateTime);
    }
} 