package com.dni9.blogapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping
  public ResponseEntity<List<PostDto>> getAllPosts() {
    return ResponseEntity.ok(postService.getAllPosts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePost(@RequestBody PostDto body, @PathVariable(name = "id") long id) {
    return ResponseEntity.ok(postService.updatePost(body, id));
  }
}
