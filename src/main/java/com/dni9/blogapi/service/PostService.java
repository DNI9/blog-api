package com.dni9.blogapi.service;

import java.util.List;

import com.dni9.blogapi.payload.PostDto;

public interface PostService {
  PostDto createPost(PostDto post);

  List<PostDto> getAllPosts();

  PostDto getPostById(long id);

  PostDto updatePost(PostDto data, long id);
}
