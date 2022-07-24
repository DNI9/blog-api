package com.dni9.blogapi.service;

import com.dni9.blogapi.payload.PostDto;
import com.dni9.blogapi.payload.PostResponse;

public interface PostService {
  PostDto createPost(PostDto post);

  PostResponse getAllPosts(int pageNo, int pageSize);

  PostDto getPostById(long id);

  PostDto updatePost(PostDto data, long id);

  void deletePost(long id);
}
