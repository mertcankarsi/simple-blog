package com.mertcankarsi.simpleblog.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handlePostNotFoundException_ShouldReturnErrorResponse() {
        PostNotFoundException exception = new PostNotFoundException("ref-key");

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handlePostNotFoundException(exception);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Post not found with reference key: ref-key", response.getBody().get("message"));
    }

    @Test
    void handleValidationException_ShouldReturnErrorResponse() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("postDto", "title", "Title cannot be empty");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleValidationException(exception);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("title: Title cannot be empty", response.getBody().get("message"));
    }

    @Test
    void handleGenericException_ShouldReturnErrorResponse() {
        Exception exception = new Exception("Unexpected error");

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleGenericException(exception);

        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("An unexpected error occurred", response.getBody().get("message"));
    }
}