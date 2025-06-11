package org.example.restaurantmanagementapplication.service;

import jakarta.transaction.Transactional;
import org.example.restaurantmanagementapplication.repository.RoleRepository;
import org.example.restaurantmanagementapplication.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	public Role findById(int id) {
		Optional<Role> result = roleRepository.findById(id);
		return result.orElse(null);
	}

	public Role findByName(String name) {
		Optional<Role> result = roleRepository.findByName(name);
		return result.orElse(null);
	}

	@Transactional
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Transactional
	public void deleteById(int id) {
		roleRepository.deleteById(id);
	}
}
