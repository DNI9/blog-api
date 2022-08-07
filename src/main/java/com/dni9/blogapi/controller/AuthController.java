package com.dni9.blogapi.controller;

import com.dni9.blogapi.entity.Role;
import com.dni9.blogapi.entity.User;
import com.dni9.blogapi.payload.LoginDto;
import com.dni9.blogapi.payload.SignupDto;
import com.dni9.blogapi.repository.RoleRepository;
import com.dni9.blogapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthController(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/signin")
  public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginDto data) {
    System.out.println(data);
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(data.getUsernameOrEmail(), data.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return new ResponseEntity<>("User signed-in successfully   ", HttpStatus.OK);
  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@Valid @RequestBody SignupDto data) {

    if (userRepository.existsByUsername(data.getUsername())) {
      return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
    }

    if (userRepository.existsByEmail(data.getEmail())) {
      return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
    }

    User user = new User();
    user.setName(data.getName());
    user.setUsername(data.getUsername());
    user.setEmail(data.getEmail());
    user.setPassword(passwordEncoder.encode(data.getPassword()));

    Role roles = roleRepository.findByName("ROLE_ADMIN").get();
    user.setRoles(Collections.singleton(roles));

    userRepository.save(user);

    return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
  }
}
