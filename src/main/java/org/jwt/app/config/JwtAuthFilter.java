package org.jwt.app.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.jwt.app.utils.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;
  private final JwtUtils jwt;

  public JwtAuthFilter(UserDetailsService userDetailsService, JwtUtils jwt) {
    this.userDetailsService = userDetailsService;
    this.jwt = jwt;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      var token = authorizationHeader.replace("Bearer ", "");
      try {
        var username = jwt.verify(token);
        authenticateUser(username, request);
      } catch (TokenExpiredException e) {

        try {
          var refreshedToken = jwt.refresh(token);
          response.setHeader("Authorization", "Bearer " + refreshedToken);
          var username = jwt.getUsername(token);
          authenticateUser(username, request);
        } catch (Exception ex) {

          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write("Token refresh failed: " + ex.getMessage());
          return;
        }
      }
    }

    filterChain.doFilter(request, response);
  }

  private void authenticateUser(String username, HttpServletRequest request) {
    var user = userDetailsService.loadUserByUsername(username);
    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    var context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);
  }
}