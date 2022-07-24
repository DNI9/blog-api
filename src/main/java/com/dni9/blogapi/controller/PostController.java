package com.dni9.blogapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dni9.blogapi.payload.PostDto;
import com.dni9.blogapi.payload.PostResponse;
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
  public ResponseEntity<PostResponse> getAllPosts(
      @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
    return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePost(@RequestBody PostDto body, @PathVariable(name = "id") long id) {
    return ResponseEntity.ok(postService.updatePost(body, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
    postService.deletePost(id);
    return ResponseEntity.ok(String.format("Post deleted with id %d", id));
  }
}
