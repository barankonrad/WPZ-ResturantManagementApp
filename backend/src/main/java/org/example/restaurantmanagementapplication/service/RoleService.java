package org.example.restaurantmanagementapplication.service;

import org.example.restaurantmanagementapplication.entity.Role;

import java.util.List;

public interface RoleService {
	List<Role> findAll();

	Role findById(int id);

	Role save(Role role);

	void deleteById(int id);
}
