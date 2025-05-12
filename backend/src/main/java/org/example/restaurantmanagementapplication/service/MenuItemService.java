package org.example.restaurantmanagementapplication.service;

import java.util.List;
import org.example.restaurantmanagementapplication.entity.MenuItem;

public interface MenuItemService {
  List<MenuItem> findAll();

  List<MenuItem> findAvailable();

  MenuItem findById(Long id);

  MenuItem save(MenuItem menuItem);

  void deleteById(Long id);
}
