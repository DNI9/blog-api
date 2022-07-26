package com.dni9.blogapi.service;

import java.util.List;

import com.dni9.blogapi.payload.CommentDto;

public interface CommentService {
  CommentDto createComment(long postId, CommentDto data);

  List<CommentDto> getCommentsByPostId(long postId);

  CommentDto getCommentById(long postId, long commentId);

  CommentDto updateComment(long postId, long commentId, CommentDto comment);

  void deleteComment(long postId, long commentId);
}
