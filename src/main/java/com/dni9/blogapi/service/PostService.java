package com.dni9.blogapi.service;

import com.dni9.blogapi.payload.PostDto;

public interface PostService {
  PostDto createPost(PostDto post);
}
