package com.dni9.blogapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dni9.blogapi.payload.PostDto;
import com.dni9.blogapi.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto body) {
    return new ResponseEntity<>(postService.createPost(body), HttpStatus.CREATED);
  }
}
