package com.dni9.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CommentDto {
  private long id;
  private String name;
  private String email;
  private String body;
}
