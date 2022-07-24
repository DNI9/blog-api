package com.dni9.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dni9.blogapi.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
