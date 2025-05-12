package org.example.restaurantmanagementapplication.rest;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.service.MenuItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
public class MenuItemController {
  private final MenuItemService menuItemService;

  @GetMapping
  public ResponseEntity<List<MenuItem>> getAllMenuItems() {
    List<MenuItem> menuItems = menuItemService.findAll();
    return ResponseEntity.ok(menuItems);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
    MenuItem menuItem = menuItemService.findById(id);
    if (menuItem != null) {
      return ResponseEntity.ok(menuItem);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
    MenuItem createdMenuItem = menuItemService.save(menuItem);
    return ResponseEntity.ok(createdMenuItem);
  }

  @PutMapping
  public ResponseEntity<MenuItem> updateMenuItem(@RequestBody MenuItem menuItem) {
    if (menuItemService.findById(menuItem.getId()) == null) {
      return ResponseEntity.notFound().build();
    }
    MenuItem updatedMenuItem = menuItemService.save(menuItem);
    return ResponseEntity.ok(updatedMenuItem);
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
