package org.example.restaurantmanagementapplication.rest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.model.in.LoginRequest;
import org.example.restaurantmanagementapplication.model.out.UserOut;
import org.example.restaurantmanagementapplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final HttpSession httpSession;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<UserOut> login(@RequestBody LoginRequest loginRequest) {
    try {
      var authenticationToken = new UsernamePasswordAuthenticationToken(
          loginRequest.getEmail(), loginRequest.getPassword());

      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

      final var currentUser = getCurrentUser(authentication);
      return ResponseEntity.ok().body(UserOut.fromEntity(currentUser));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @GetMapping("/me")
  public ResponseEntity<UserOut> me() {
    final var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal()
        .equals("anonymousUser")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    final var currentUser = getCurrentUser(authentication);
    return ResponseEntity.ok().body(UserOut.fromEntity(currentUser));
  }

  private User getCurrentUser(Authentication authentication) {
    final var username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
    return userService.findByUsername(username);
  }
}