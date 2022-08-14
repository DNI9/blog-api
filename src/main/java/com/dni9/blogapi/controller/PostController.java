package com.dni9.blogapi.controller;

import com.dni9.blogapi.payload.ErrorDetails;
import com.dni9.blogapi.payload.PostDto;
import com.dni9.blogapi.payload.PostResponse;
import com.dni9.blogapi.service.PostService;
import com.dni9.blogapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Posts")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PreAuthorize(AppConstants.HAS_ROLE_ADMIN)
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Create a blog post", responses = {
      @ApiResponse(content = @Content(schema = @Schema(implementation = PostDto.class)), responseCode = "201"),
  })
  public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto body) {
    return new ResponseEntity<>(postService.createPost(body), HttpStatus.CREATED);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get list of posts")
  public ResponseEntity<PostResponse> getAllPosts(@RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo, @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize, @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy, @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) Direction sortDir) {
    return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get a single post by id", responses = {
      @ApiResponse(content = @Content(schema = @Schema(implementation = PostDto.class)), responseCode = "200"),
      @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
  public ResponseEntity<PostDto> getPostById(
      @Parameter(description = "Post id to find") @PathVariable(name = "id") final long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @PreAuthorize(AppConstants.HAS_ROLE_ADMIN)
  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Update a single post", responses = {
      @ApiResponse(content = @Content(schema = @Schema(implementation = PostDto.class)), responseCode = "200"),
      @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
  public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto body, @PathVariable(name = "id") long id) {
    return ResponseEntity.ok(postService.updatePost(body, id));
  }

  @PreAuthorize(AppConstants.HAS_ROLE_ADMIN)
  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Delete a single post", responses = {
      @ApiResponse(responseCode = "200"),
      @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
  public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
    postService.deletePost(id);
    return ResponseEntity.ok(String.format("Post deleted with id %d", id));
  }
}
