package org.example.restaurantmanagementapplication.repository;

import java.util.List;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

  List<MenuItem> findByIsAvailableTrue();
}
