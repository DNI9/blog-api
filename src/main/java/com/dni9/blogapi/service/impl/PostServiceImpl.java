package com.dni9.blogapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dni9.blogapi.entity.Post;
import com.dni9.blogapi.exception.ResourceNotFoundException;
import com.dni9.blogapi.payload.PostDto;
import com.dni9.blogapi.payload.PostResponse;
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

  @Override
  public PostResponse getAllPosts(int pageNo, int pageSize) {
    PageRequest pageable = PageRequest.of(pageNo, pageSize);
    Page<Post> posts = postRepository.findAll(pageable);

    List<PostDto> listOfPosts = posts.getContent()
        .stream().map(this::mapToDto).collect(Collectors.toList());

    return PostResponse.builder()
        .content(listOfPosts)
        .pageNo(posts.getNumber())
        .pageSize(posts.getSize())
        .totalElements(posts.getTotalElements())
        .totalPages(posts.getTotalPages())
        .last(posts.isLast())
        .build();
  }

  @Override
  public PostDto getPostById(long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

    return mapToDto(post);
  }

  @Override
  public PostDto updatePost(PostDto data, long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

    post.setTitle(data.getTitle());
    post.setDescription(data.getDescription());
    post.setContent(data.getContent());

    Post updatedPost = postRepository.save(post);
    return mapToDto(updatedPost);
  }

  @Override
  public void deletePost(long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

    postRepository.delete(post);
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
