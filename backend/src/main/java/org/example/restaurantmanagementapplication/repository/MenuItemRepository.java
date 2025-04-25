package org.example.restaurantmanagementapplication.repository;

import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

}
