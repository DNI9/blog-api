package com.dni9.blogapi.security;

import com.dni9.blogapi.exception.BlogApiException;
import com.dni9.blogapi.utils.AppConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  public String generateToken(Authentication authentication) {
    String username = authentication.getName();

    Date currentDate = new Date();
    Date expiryDate = new Date(currentDate.getTime() + AppConstants.JWT_EXPIRY_DATE);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(currentDate)
        .setExpiration(expiryDate)
        .signWith(key)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
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
