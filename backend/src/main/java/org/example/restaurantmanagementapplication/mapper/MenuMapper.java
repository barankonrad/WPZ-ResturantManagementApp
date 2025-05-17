package org.example.restaurantmanagementapplication.mapper;

import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.model.out.MenuItemForMenuDTO;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper implements GenericMapper<MenuItem, MenuItemForMenuDTO> {
  @Override
  public MenuItemForMenuDTO toDTO(MenuItem menuItem) {
    if (menuItem == null) {
      return null;
    }
    return new MenuItemForMenuDTO(
        menuItem.getId(), menuItem.getName(), menuItem.getDescription(), menuItem.getPrice());
  }

  @Override
  public MenuItem toEntity(MenuItemForMenuDTO menuItemForMenuDTO) {
    return null;
  }
}
