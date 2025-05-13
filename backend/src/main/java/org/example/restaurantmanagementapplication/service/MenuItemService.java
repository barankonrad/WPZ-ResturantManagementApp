package org.example.restaurantmanagementapplication.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemService {
  private final MenuItemRepository menuItemRepository;

  public List<MenuItem> findAll() {
    return menuItemRepository.findAll();
  }

  public List<MenuItem> findAvailable() {
    return menuItemRepository.findByIsAvailableTrue();
  }

  public MenuItem findById(Long id) {
    return menuItemRepository.findById(id).orElse(null);
  }

  @Transactional
  public MenuItem save(MenuItem menuItem) {
    return menuItemRepository.save(menuItem);
  }

  @Transactional
  public void deleteById(Long id) {
    menuItemRepository.deleteById(id);
  }
}
