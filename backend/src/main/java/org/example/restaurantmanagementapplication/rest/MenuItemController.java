package org.example.restaurantmanagementapplication.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.mapper.MenuItemMapper;
import org.example.restaurantmanagementapplication.model.MenuItemDTO;
import org.example.restaurantmanagementapplication.service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
public class MenuItemController {
  private final MenuItemService menuItemService;
  private final MenuItemMapper menuItemMapper;

  @GetMapping
  public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
    List<MenuItem> menuItems = menuItemService.findAll();
    List<MenuItemDTO> menuItemDTOs = menuItems.stream().map(menuItemMapper::toDTO).toList();
    return ResponseEntity.ok(menuItemDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id) {
    MenuItem menuItem = menuItemService.findById(id);
    if (menuItem != null) {
      return ResponseEntity.ok(menuItemMapper.toDTO(menuItem));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<MenuItemDTO> createMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
    MenuItem menuItem = menuItemMapper.toEntity(menuItemDTO);
    MenuItem createdMenuItem = menuItemService.save(menuItem);
    return ResponseEntity.ok(menuItemMapper.toDTO(createdMenuItem));
  }

  @PutMapping
  public ResponseEntity<MenuItemDTO> updateMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
    MenuItem menuItem = menuItemMapper.toEntity(menuItemDTO);
    if (menuItemService.findById(menuItem.getId()) == null) {
      return ResponseEntity.notFound().build();
    }
    MenuItem updatedMenuItem = menuItemService.save(menuItem);
    return ResponseEntity.ok(menuItemMapper.toDTO(updatedMenuItem));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
    if (menuItemService.findById(id) == null) {
      return ResponseEntity.notFound().build();
    }
    menuItemService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
