package org.example.restaurantmanagementapplication.mapper;

import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.model.out.MenuItemDTO;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper implements GenericMapper<MenuItem, MenuItemDTO> {

  @Override
  public MenuItemDTO toDTO(MenuItem menuItem) {
    if (menuItem == null) {
      return null;
    }
    return new MenuItemDTO(
        menuItem.getId(),
        menuItem.getName(),
        menuItem.getDescription(),
        menuItem.getPrice(),
        menuItem.isAvailable());
  }

  @Override
  public MenuItem toEntity(MenuItemDTO menuItemDTO) {
    if (menuItemDTO == null) {
      return null;
    }
    MenuItem menuItem = new MenuItem();
    menuItem.setId(menuItemDTO.getId());
    menuItem.setName(menuItemDTO.getName());
    menuItem.setDescription(menuItemDTO.getDescription());
    menuItem.setPrice(menuItemDTO.getPrice());
    menuItem.setAvailable(menuItemDTO.isAvailable());
    return menuItem;
  }
}
