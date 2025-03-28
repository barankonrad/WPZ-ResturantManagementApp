package org.example.restaurantmanagementapplication.repository;

import org.example.restaurantmanagementapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>, CustomUserRepository {
}
