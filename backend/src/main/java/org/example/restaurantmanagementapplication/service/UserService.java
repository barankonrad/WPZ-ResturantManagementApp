package org.example.restaurantmanagementapplication.service;

import org.example.restaurantmanagementapplication.entity.Role;
import org.example.restaurantmanagementapplication.entity.User;

import java.util.List;

public interface UserService {
	List<User> findAll();

	User findById(int id);

	User save(User user);

	void deleteById(int id);

	Role findRole(int id);
}
