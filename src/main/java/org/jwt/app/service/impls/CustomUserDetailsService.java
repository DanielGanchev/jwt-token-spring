package org.jwt.app.service.impls;


import java.util.Collection;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.jwt.app.models.entities.User;
import org.jwt.app.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


  private final UserRepository userRepository; // Your UserRepository

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        true, true, true, true, getAuthorities(user));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(User user) {

    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
  }
}