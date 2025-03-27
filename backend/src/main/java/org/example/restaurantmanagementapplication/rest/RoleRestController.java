package org.example.restaurantmanagementapplication.rest;

import jakarta.validation.Valid;
import org.example.restaurantmanagementapplication.common.PropertyUtils;
import org.example.restaurantmanagementapplication.entity.Role;
import org.example.restaurantmanagementapplication.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleRestController {
	private final RoleService roleService;

	public RoleRestController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping("/roles")
	public ResponseEntity<List<Role>> retrieveAllRoles() {
		return ResponseEntity.ok(roleService.findAll());
	}

	@GetMapping("/roles/{id}")
	public ResponseEntity<Role> retrieveRole(@PathVariable int id) {
		Role role = roleService.findById(id);
		if (role != null) {
			return ResponseEntity.ok(role);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/roles")
	public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
		roleService.save(role);
		return ResponseEntity.ok(role);
	}

	@PutMapping("/roles")
	public ResponseEntity<Role> replaceRole(@Valid @RequestBody Role role) {
		if (roleService.findById(role.getId()) == null) {
			return ResponseEntity.notFound().build();
		}
		roleService.save(role);
		return ResponseEntity.ok(role);
	}

	@PatchMapping("/roles/{id}")
	public ResponseEntity<Role> updateRole(@Valid @RequestBody Role newRole, @PathVariable int id) {
		Role role = roleService.findById(id);
		if (role != null) {
			BeanUtils.copyProperties(newRole, role, PropertyUtils.getNullPropertyNames(newRole));
			role.setId(id);
			role = roleService.save(role);
			return ResponseEntity.ok(role);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/roles/{id}")
	public ResponseEntity<?> deleteRole(@PathVariable int id) {
		Role role = roleService.findById(id);
		if (role != null) {
			roleService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
