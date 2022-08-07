package com.dni9.blogapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginDto {

  @NotEmpty(message = "username or email must not be empty")
  @Size(min = 3, max = 50, message = "username or email must be 3 character long")
  private String usernameOrEmail;

  @NotEmpty(message = "Password must not be empty")
  @Size(min = 3, max = 30, message = "Password must be min 3 character long")
  private String password;
}
