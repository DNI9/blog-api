package com.dni9.blogapi.service;

import java.util.List;

import com.dni9.blogapi.payload.CommentDto;

public interface CommentService {
  CommentDto createComment(long postId, CommentDto data);

  List<CommentDto> getCommentsByPostId(long postId);
}
