package com.dni9.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignupDto {

  @NotEmpty(message = "Name must not be empty")
  @Size(min = 3, max = 100, message = "Name must be 3 character long")
  private String name;

  @NotEmpty(message = "username must not be empty")
  @Size(min = 3, max = 15, message = "username must be 3 character long")
  private String username;

  @NotEmpty(message = "Email must not be empty")
  @Email
  private String email;

  @NotEmpty(message = "Password must not be empty")
  @Size(min = 3, max = 30, message = "Password must be min 3 character long")
  private String password;
}
