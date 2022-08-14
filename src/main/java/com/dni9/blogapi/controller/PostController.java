package com.dni9.blogapi.controller;

import com.dni9.blogapi.payload.PostDto;
import com.dni9.blogapi.payload.PostResponse;
import com.dni9.blogapi.service.PostService;
import com.dni9.blogapi.utils.AppConstants;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PreAuthorize(AppConstants.HAS_ROLE_ADMIN)
  @PostMapping
  public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto body) {
    return new ResponseEntity<>(postService.createPost(body), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<PostResponse> getAllPosts(
      @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
      @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
      @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
      @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) Direction sortDir) {
    return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @PreAuthorize(AppConstants.HAS_ROLE_ADMIN)
  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto body, @PathVariable(name = "id") long id) {
    return ResponseEntity.ok(postService.updatePost(body, id));
  }

  @PreAuthorize(AppConstants.HAS_ROLE_ADMIN)
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
    postService.deletePost(id);
    return ResponseEntity.ok(String.format("Post deleted with id %d", id));
  }
}
