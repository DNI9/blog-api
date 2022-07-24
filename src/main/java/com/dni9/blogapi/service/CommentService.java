package com.dni9.blogapi.service;

import com.dni9.blogapi.payload.CommentDto;

public interface CommentService {
  CommentDto createComment(long postId, CommentDto data);
}
