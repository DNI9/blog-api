package com.dni9.blogapi.service.impl;

import org.springframework.stereotype.Service;

import com.dni9.blogapi.entity.Post;
import com.dni9.blogapi.payload.PostDto;
import com.dni9.blogapi.repository.PostRepository;
import com.dni9.blogapi.service.PostService;

@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  public PostServiceImpl(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public PostDto createPost(PostDto data) {
    Post post = new Post();
    post.setTitle(data.getTitle());
    post.setDescription(data.getDescription());
    post.setContent(post.getContent());

    Post newPost = postRepository.save(post);

    return PostDto.builder()
        .id(newPost.getId())
        .title(newPost.getTitle())
        .description(newPost.getDescription())
        .content(newPost.getContent())
        .build();
  }
}
