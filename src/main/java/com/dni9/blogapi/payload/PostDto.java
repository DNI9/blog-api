package com.dni9.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostDto {
  private Long id;
  private String title;
  private String description;
  private String content;
}
