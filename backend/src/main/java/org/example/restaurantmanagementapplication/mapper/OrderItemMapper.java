package org.example.restaurantmanagementapplication.mapper;

import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.model.out.OrderItemDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapper implements GenericMapper<OrderItem, OrderItemDto> {

  private final MenuItemMapper menuItemMapper;

  @Override
  public OrderItemDto toDTO(OrderItem entity) {
    if (entity == null) {
      return null;
    }
    MenuItem menuItem = entity.getMenuItem();
    return new OrderItemDto(
        menuItemMapper.toDTO(menuItem),
        entity.getQuantity(),
        entity.getPrice()
    );
  }

  @Override
  public OrderItem toEntity(OrderItemDto dto) {
    if (dto == null) {
      return null;
    }
    OrderItem orderItem = new OrderItem();
    orderItem.setMenuItem(menuItemMapper.toEntity(dto.getMenuItem()));
    orderItem.setQuantity(dto.getQuantity());
    orderItem.setPrice(dto.getPrice());
    return orderItem;
  }
}
