package com.dni9.blogapi.controller;

import com.dni9.blogapi.payload.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  public AuthController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/signin")
  public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginDto data) {
    System.out.println(data);
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(data.getUsernameOrEmail(), data.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return new ResponseEntity<>("User signed-in successfully   ", HttpStatus.OK);
  }
}
