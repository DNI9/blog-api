package com.dni9.blogapi.service.impl;

import org.springframework.stereotype.Service;

import com.dni9.blogapi.entity.Comment;
import com.dni9.blogapi.entity.Post;
import com.dni9.blogapi.exception.ResourceNotFoundException;
import com.dni9.blogapi.payload.CommentDto;
import com.dni9.blogapi.repository.CommentRepository;
import com.dni9.blogapi.repository.PostRepository;
import com.dni9.blogapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
  }

  @Override
  public CommentDto createComment(long postId, CommentDto data) {
    Comment comment = mapToEntity(data);
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    comment.setPost(post);
    Comment newComment = commentRepository.save(comment);
    return mapToDto(newComment);
  }

  private Comment mapToEntity(CommentDto data) {
    Comment comment = new Comment();

    comment.setName(data.getName());
    comment.setEmail(data.getEmail());
    comment.setBody(data.getBody());

    return comment;
  }

  private CommentDto mapToDto(Comment comment) {
    return CommentDto.builder()
        .id(comment.getId())
        .name(comment.getName())
        .email(comment.getEmail())
        .body(comment.getBody())
        .build();
  }
}
