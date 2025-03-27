package org.example.restaurantmanagementapplication.rest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.model.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final AuthenticationManager authenticationManager;
  private final HttpSession httpSession;

  @PostMapping("/login")
  public ResponseEntity<Authentication> login(@RequestBody LoginRequest loginRequest) {
    try {
      var authenticationToken = new UsernamePasswordAuthenticationToken(
          loginRequest.getUsername(), loginRequest.getPassword());

      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

      return ResponseEntity.ok().body(authentication);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }
}