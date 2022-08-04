package com.dni9.blogapi.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.dni9.blogapi.payload.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {

  // handle specific exceptions
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
      ResourceNotFoundException exception,
      WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(
        new Date(),
        exception.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BlogApiException.class)
  public ResponseEntity<ErrorDetails> handleBlogApiException(
      BlogApiException exception,
      WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(
        new Date(),
        exception.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
