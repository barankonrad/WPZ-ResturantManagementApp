package org.example.restaurantmanagementapplication.common;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionManager {

  private final UserService userService;

  public Optional<User> getCurrentUser() {
    final var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal()
        .equals("anonymousUser")) {
      return Optional.empty();
    }

    final var username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
    final var user = userService.findByUsername(username);
    return Optional.ofNullable(user);
  }

}
