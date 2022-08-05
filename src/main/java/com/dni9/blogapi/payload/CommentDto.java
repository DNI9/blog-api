package com.dni9.blogapi.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
  private long id;

  @NotEmpty(message = "Name should not be null of empty")
  @Size(min = 2, message = "name should have at least 2 characters")
  private String name;

  @NotEmpty(message = "Email should not be null of empty")
  @Email(message = "Provided email is not valid")
  private String email;

  @NotEmpty
  @Size(min = 10, message = "Comment body should have at least 10 characters")
  private String body;
}
