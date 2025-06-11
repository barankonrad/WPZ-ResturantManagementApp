package org.example.restaurantmanagementapplication.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.Role;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.repository.RoleRepository;
import org.example.restaurantmanagementapplication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User findById(int id) {
    Optional<User> result = userRepository.findById(id);
    return result.orElse(null);
  }

  public User findByUsername(String username) {
    Optional<User> result = userRepository.findByEmail(username);
    return result.orElse(null);
  }

  @Transactional
  public User save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Transactional
  public void deleteById(int id) {
    userRepository.deleteById(id);
  }

  public Role findRole(int id) {
    Optional<Role> role = roleRepository.findById(id);
    return role.orElse(null);
  }
}
