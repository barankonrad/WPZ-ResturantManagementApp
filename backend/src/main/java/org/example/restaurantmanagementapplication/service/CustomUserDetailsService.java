package org.example.restaurantmanagementapplication.service;

import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userService
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
        .password(user.getPassword())
        .authorities(getRoleName(user))
        .build();
  }

  private String getRoleName(User user) {
    return user.getRole().getName();
  }
}
