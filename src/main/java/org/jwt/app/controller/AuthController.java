package org.jwt.app.controller;


import org.jwt.app.models.dtos.AuthRequest;
import org.jwt.app.models.entities.User;
import org.jwt.app.service.UserService;
import org.jwt.app.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtUtils jwt;

  public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtils jwt) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.jwt = jwt;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.email(), request.password())
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      String jwtToken = jwt.generate(userDetails.getUsername());
      return ResponseEntity.ok().body(jwtToken);
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Invalid credentials");
    }
  }

  @PostMapping("/register")

  public ResponseEntity<?> register(@RequestBody AuthRequest request) {
    if (userService.isUserExist(request.email())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
    }
    User user = new User();
    user.setEmail(request.email());
    user.setPassword(request.password());
    userService.register(user);
    return ResponseEntity.ok("User registered successfully");
  }


}