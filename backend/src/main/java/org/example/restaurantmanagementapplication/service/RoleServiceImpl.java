package org.example.restaurantmanagementapplication.service;

import jakarta.transaction.Transactional;
import org.example.restaurantmanagementapplication.repository.RoleRepository;
import org.example.restaurantmanagementapplication.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findById(int id) {
		Optional<Role> result = roleRepository.findById(id);
		return result.orElse(null);
	}

	@Override
	@Transactional
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		roleRepository.deleteById(id);
	}
}
