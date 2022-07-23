package com.dni9.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dni9.blogapi.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
