package org.example.restaurantmanagementapplication.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.mapper.MenuMapper;
import org.example.restaurantmanagementapplication.model.MenuItemForMenuDTO;
import org.example.restaurantmanagementapplication.service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
  private final MenuItemService menuItemService;
  private final MenuMapper menuMapper;

  @GetMapping
  public ResponseEntity<List<MenuItemForMenuDTO>> displayMenu() {
    List<MenuItem> menuItems = menuItemService.findAvailable();
    List<MenuItemForMenuDTO> menuItemForMenuDTOS = menuItems.stream().map(menuMapper::toDTO).toList();
    return ResponseEntity.ok(menuItemForMenuDTOS);
  }
}
