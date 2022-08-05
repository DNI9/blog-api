package com.dni9.blogapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dni9.blogapi.payload.CommentDto;
import com.dni9.blogapi.service.CommentService;

@RestController
@RequestMapping("/api/")
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
