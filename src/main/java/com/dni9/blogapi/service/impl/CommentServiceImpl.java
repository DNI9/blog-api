package com.dni9.blogapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dni9.blogapi.entity.Comment;
import com.dni9.blogapi.entity.Post;
import com.dni9.blogapi.exception.BlogApiException;
import com.dni9.blogapi.exception.ResourceNotFoundException;
import com.dni9.blogapi.payload.CommentDto;
import com.dni9.blogapi.repository.CommentRepository;
import com.dni9.blogapi.repository.PostRepository;
import com.dni9.blogapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final ModelMapper mapper;

  public CommentServiceImpl(
      CommentRepository commentRepository,
      PostRepository postRepository,
      ModelMapper modelMapper) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.mapper = modelMapper;
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

  @Override
  public List<CommentDto> getCommentsByPostId(long postId) {
    List<Comment> comments = commentRepository.findByPostId(postId);
    return comments.stream().map(this::mapToDto).collect(Collectors.toList());
  }

  @Override
  public CommentDto getCommentById(long postId, long commentId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }

    return mapToDto(comment);
  }

  @Override
  public CommentDto updateComment(long postId, long commentId, CommentDto data) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }

    comment.setName(data.getName());
    comment.setEmail(data.getEmail());
    comment.setBody(data.getBody());

    Comment updatedComment = commentRepository.save(comment);
    return mapToDto(updatedComment);
  }

  @Override
  public void deleteComment(long postId, long commentId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
    }

    commentRepository.delete(comment);
  }

  private CommentDto mapToDto(Comment comment) {
    return mapper.map(comment, CommentDto.class);
  }

  private Comment mapToEntity(CommentDto data) {
    return mapper.map(data, Comment.class);
  }
}
