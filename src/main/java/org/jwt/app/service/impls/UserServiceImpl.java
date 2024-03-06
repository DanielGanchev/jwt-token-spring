package org.jwt.app.service.impls;

import lombok.RequiredArgsConstructor;
import org.jwt.app.models.entities.User;
import org.jwt.app.repository.UserRepository;
import org.jwt.app.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  public User register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }



  @Override
  public boolean isUserExist(String email) {
    return userRepository.findByEmail(email).isPresent();
  }









}