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
    Post post = mapToEntity(data);
    Post newPost = postRepository.save(post);
    return mapToDto(newPost);
  }

  private PostDto mapToDto(Post post) {
    return PostDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .description(post.getDescription())
        .build();
  }

  private Post mapToEntity(PostDto data) {
    Post post = new Post();
    post.setTitle(data.getTitle());
    post.setDescription(data.getDescription());
    post.setContent(data.getContent());

    return post;
  }
}
