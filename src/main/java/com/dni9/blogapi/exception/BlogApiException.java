package com.dni9.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BlogApiException extends RuntimeException {
  private HttpStatus status; // FIXME: not utilizing the status
  private String message;

  public BlogApiException(String message, HttpStatus status, String message1) {
    super(message);
    this.status = status;
    message = message1;
  }
}
