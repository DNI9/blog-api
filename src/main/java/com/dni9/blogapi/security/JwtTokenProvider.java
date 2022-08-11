package com.dni9.blogapi.security;

import com.dni9.blogapi.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

  @Value("${app.jwt-secret}")
  private String jwtSecret;

  @Value("${app.jwt-expiration-milliseconds}")
  private int jwtExpirationMillis;


  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expiryDate = new Date(currentDate.getTime() + jwtExpirationMillis);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(currentDate)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (SignatureException exception) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Signature");
    } catch (MalformedJwtException exception) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
    } catch (ExpiredJwtException exception) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT token expired");
    } catch (UnsupportedJwtException | IllegalArgumentException exception) {
      throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
    }
  }
}
