package com.dni9.blogapi.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
  private String accessToken;
  private String tokenType = "Bearer";

  public JwtAuthResponse(String token) {
    this.accessToken = token;
  }
}
