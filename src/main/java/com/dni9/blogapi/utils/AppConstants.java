package com.dni9.blogapi.utils;

public class AppConstants {
  public static final String DEFAULT_PAGE_NUMBER = "0";
  public static final String DEFAULT_PAGE_SIZE = "10";
  public static final String DEFAULT_SORT_BY = "id";
  public static final String DEFAULT_SORT_DIRECTION = "ASC";
  public static final String HAS_ROLE_ADMIN = "hasRole('ADMIN')";

  public static final int JWT_EXPIRY_DATE = 604800000; // 7 Days

  private AppConstants() {
  }
}
