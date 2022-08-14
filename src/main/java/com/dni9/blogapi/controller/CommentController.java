package com.dni9.blogapi.controller;

import com.dni9.blogapi.payload.CommentDto;
import com.dni9.blogapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/posts/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(
      @PathVariable(name = "postId") long postId,
      @Valid @RequestBody CommentDto body) {
    return new ResponseEntity<>(commentService.createComment(postId, body), HttpStatus.CREATED);
  }

  @GetMapping("/posts/{postId}/comments")
  public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(name = "postId") long postId) {
    return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
  }

  @GetMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> getCommentById(
      @PathVariable(name = "postId") long postId,
      @PathVariable(name = "id") long id) {
    return ResponseEntity.ok(commentService.getCommentById(postId, id));
  }

  @PutMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<CommentDto> updateComment(
      @PathVariable(name = "postId") long postId,
      @PathVariable(name = "id") long id,
      @Valid @RequestBody CommentDto comment) {
    return ResponseEntity.ok(commentService.updateComment(postId, id, comment));
  }

  @DeleteMapping("/posts/{postId}/comments/{id}")
  public ResponseEntity<String> deleteComment(
      @PathVariable(name = "postId") long postId,
      @PathVariable(name = "id") long id) {
    commentService.deleteComment(postId, id);
    return ResponseEntity.ok(String.format("Comment deleted with id %d", id));
  }

}
