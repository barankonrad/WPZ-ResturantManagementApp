package org.example.restaurantmanagementapplication.rest;

import jakarta.validation.Valid;
import java.util.List;
import org.example.restaurantmanagementapplication.common.JSONErrorResponse;
import org.example.restaurantmanagementapplication.common.PropertyUtils;
import org.example.restaurantmanagementapplication.entity.Role;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.model.in.RegisterRequest;
import org.example.restaurantmanagementapplication.model.out.UserOut;
import org.example.restaurantmanagementapplication.service.RoleService;
import org.example.restaurantmanagementapplication.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
  private final UserService userService;
  private final RoleService roleService;
  private static final String ROLE_NOT_FOUND = "Could not find role ";

  public UserController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> retrieveAllUsers() {
    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> retrieveUser(@PathVariable int id) {
    User user = userService.findById(id);
    if (user != null) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/users")
  public ResponseEntity<Object> createUser(@RequestBody RegisterRequest registerRequest) {
    if (PropertyUtils.countNullProperties(registerRequest) > 0) {
      return ResponseEntity.badRequest().body(new JSONErrorResponse("All fields are required"));
    }

    Role role = roleService.findByName(registerRequest.getRole());
    if (role == null) {
      return ResponseEntity.badRequest()
          .body(new JSONErrorResponse(ROLE_NOT_FOUND + registerRequest.getRole()));
    }
    User user = new User();
    user.setEmail(registerRequest.getEmail());
    user.setPassword(registerRequest.getPassword());
    user.setRole(role);
    user = userService.save(user);
    return ResponseEntity.ok(UserOut.fromEntity(user));
  }

  @PutMapping("/users")
  public ResponseEntity<Object> updateUser(@Valid @RequestBody User user) {
    if (userService.findById(user.getId()) == null) {
      return ResponseEntity.notFound().build();
    }

    if (PropertyUtils.countNullProperties(user) > 0) {
      return ResponseEntity.badRequest().body(new JSONErrorResponse("All fields are required"));
    }

    Role role = roleService.findById(user.getRole().getId());
    if (role == null) {
      return ResponseEntity.badRequest()
          .body(new JSONErrorResponse(ROLE_NOT_FOUND + user.getRole().getId()));
    } else {
      user.setRole(role);
    }

    user = userService.save(user);
    return ResponseEntity.ok(user);
  }

  @PatchMapping("/users/{id}")
  public ResponseEntity<Object> updateUser(@RequestBody User newUser, @PathVariable int id) {
    User user = userService.findById(id);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    BeanUtils.copyProperties(newUser, user, PropertyUtils.getNullPropertyNames(newUser));
    if (newUser.getRole() != null) {
      Role role = roleService.findById(newUser.getRole().getId());
      if (role == null) {
        return ResponseEntity.badRequest()
            .body(new JSONErrorResponse(ROLE_NOT_FOUND + newUser.getRole().getId()));
      } else {
        user.setRole(role);
      }
    }

    user.setId(id);
    user = userService.save(user);
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<User> deleteUser(@PathVariable int id) {
    User user = userService.findById(id);
    if (user != null) {
      userService.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
