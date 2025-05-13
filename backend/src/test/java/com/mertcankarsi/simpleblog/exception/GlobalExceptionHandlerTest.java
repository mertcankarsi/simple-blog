package com.mertcankarsi.simpleblog.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

  @InjectMocks private GlobalExceptionHandler globalExceptionHandler;

  @Test
  void handlePostNotFoundException_ShouldReturnErrorResponse() {
    PostNotFoundException exception = new PostNotFoundException("ref-key");

    ResponseEntity<Map<String, Object>> response =
        globalExceptionHandler.handlePostNotFoundException(exception);

    assertNotNull(response);
    assertEquals(404, response.getStatusCode().value());
    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("Post not found with reference key: ref-key", body.get("message"));
  }

  @Test
  void handleValidationException_ShouldReturnErrorResponse() {
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("postDto", "title", "Title cannot be empty");
    when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
    MethodArgumentNotValidException exception =
        new MethodArgumentNotValidException(Mockito.mock(MethodParameter.class), bindingResult);

    ResponseEntity<Map<String, Object>> response =
        globalExceptionHandler.handleValidationException(exception);

    assertNotNull(response);
    assertEquals(400, response.getStatusCode().value());
    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("title: Title cannot be empty", body.get("message"));
  }

  @Test
  void handleGenericException_ShouldReturnErrorResponse() {
    Exception exception = new Exception("Unexpected error");

    ResponseEntity<Map<String, Object>> response =
        globalExceptionHandler.handleGenericException(exception);

    assertNotNull(response);
    assertEquals(500, response.getStatusCode().value());
    Map<String, Object> body = response.getBody();
    assertNotNull(body);
    assertEquals("An unexpected error occurred", body.get("message"));
  }
}
