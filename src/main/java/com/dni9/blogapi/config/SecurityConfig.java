package com.dni9.blogapi.config;

import com.dni9.blogapi.security.CustomUserDetailsService;
import com.dni9.blogapi.security.JwtAuthenticationEntryPoint;
import com.dni9.blogapi.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private JwtAuthenticationEntryPoint authenticationEntryPoint;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests((authorize) -> authorize
            .antMatchers(HttpMethod.GET, "/api/**").permitAll()
            .antMatchers("/api/auth/**").permitAll()
            .anyRequest()
            .authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager
      (AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

}
