package org.example.restaurantmanagementapplication.repository;

import java.util.Optional;
import org.example.restaurantmanagementapplication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(String name);
}
