package com.dni9.blogapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
      @RequestBody CommentDto body) {
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

}
