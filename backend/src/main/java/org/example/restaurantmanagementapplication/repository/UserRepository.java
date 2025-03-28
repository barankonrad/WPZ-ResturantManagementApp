package org.example.restaurantmanagementapplication.repository;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.example.restaurantmanagementapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>, CustomUserRepository {

  Optional<User> findByEmail(@NotNull(message = "E-mail cannot be empty") String email);
}
