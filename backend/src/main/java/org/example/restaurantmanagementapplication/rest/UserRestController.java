package org.example.restaurantmanagementapplication.rest;

import jakarta.validation.Valid;
import org.example.restaurantmanagementapplication.common.JSONErrorResponse;
import org.example.restaurantmanagementapplication.common.PropertyUtils;
import org.example.restaurantmanagementapplication.entity.Role;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.service.RoleService;
import org.example.restaurantmanagementapplication.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class UserRestController {
	private final UserService userService;
	private final RoleService roleService;

	public UserRestController(UserService userService, RoleService roleService) {
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
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		if (!user.getRoles().isEmpty()) {
			Collection<Role> persistedRoles = new ArrayList<>();
			for (Role userRole : user.getRoles()) {
				Role role = roleService.findById(userRole.getId());
				if (role != null) {
					persistedRoles.add(role);
				} else {
					return ResponseEntity.badRequest().build();
				}
			}
			user.setRoles(persistedRoles);
		}
		user = userService.save(user);
		return ResponseEntity.ok(user);
	}

	@PutMapping("/users")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
		if (userService.findById(user.getId()) == null) {
			return ResponseEntity.notFound().build();
		}

		if (!user.getRoles().isEmpty()) {
			Collection<Role> persistedRoles = new ArrayList<>();
			for (Role userRole : user.getRoles()) {
				Role role = roleService.findById(userRole.getId());
				if (role != null) {
					persistedRoles.add(role);
				} else {
					return ResponseEntity.badRequest().build();
				}
			}
			user.setRoles(persistedRoles);
		}

		user = userService.save(user);
		return ResponseEntity.ok(user);
	}

	@PatchMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser, @PathVariable int id) {
		if (!newUser.getRoles().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		User user = userService.findById(id);

		if (user != null) {
			BeanUtils.copyProperties(newUser, user, PropertyUtils.getNullPropertyNames(newUser));
			user.setId(id);
			user = userService.save(user);
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		User user = userService.findById(id);
		if (user != null) {
			userService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/users/{userId}/add")
	public ResponseEntity<?> addRoleToUser(@PathVariable int userId, @RequestBody Role newRole) {
		User user = userService.findById(userId);
		Role role = userService.findRole(newRole.getId());
		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		if (role != null) {
			if (user.getRoles().contains(role)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new JSONErrorResponse("User already has this role assigned"));
			}
			user.addRole(role);
			user = userService.save(user);
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/users/{userId}/remove")
	public ResponseEntity<?> removeRoleFromUser(@PathVariable int userId, @RequestBody Role newRole) {
		User user = userService.findById(userId);
		Role role = userService.findRole(newRole.getId());
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		if (role != null) {
			if (!user.getRoles().contains(role)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(new JSONErrorResponse("User does not have this role assigned"));
			} else {
				user.removeRole(role);
				user = userService.save(user);
				return ResponseEntity.ok(user);
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
