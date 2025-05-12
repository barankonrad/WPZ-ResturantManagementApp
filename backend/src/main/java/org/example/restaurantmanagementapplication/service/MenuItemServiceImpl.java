package org.example.restaurantmanagementapplication.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
  private final MenuItemRepository menuItemRepository;

  @Override
  public List<MenuItem> findAll() {
    return menuItemRepository.findAll();
  }

  @Override
  public List<MenuItem> findAvailable() {
    return menuItemRepository.findByIsAvailableTrue();
  }

  @Override
  public MenuItem findById(Long id) {
    return menuItemRepository.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public MenuItem save(MenuItem menuItem) {
    return menuItemRepository.save(menuItem);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    menuItemRepository.deleteById(id);
  }
}
