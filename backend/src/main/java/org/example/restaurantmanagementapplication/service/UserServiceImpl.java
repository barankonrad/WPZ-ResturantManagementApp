package org.example.restaurantmanagementapplication.service;

import org.example.restaurantmanagementapplication.repository.RoleRepository;
import org.example.restaurantmanagementapplication.repository.UserRepository;
import org.example.restaurantmanagementapplication.entity.Role;
import org.example.restaurantmanagementapplication.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(int id) {
		Optional<User> result = userRepository.findById(id);
		return result.orElse(null);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public Role findRole(int id) {
		Optional<Role> role = roleRepository.findById(id);
		return role.orElse(null);
	}

}
