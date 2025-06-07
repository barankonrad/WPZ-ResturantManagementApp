package org.example.restaurantmanagementapplication.mapper;

import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.model.out.BillOrderItemDto;
import org.springframework.stereotype.Component;

@Component
public class BillOrderItemMapper implements GenericMapper<OrderItem, BillOrderItemDto> {

  @Override
  public BillOrderItemDto toDTO(OrderItem entity) {
    if (entity == null) {
      return null;
    }

    var dto = new BillOrderItemDto();
    dto.setName(entity.getMenuItem().getName());
    dto.setPrice(entity.getPrice());
    dto.setQuantity(entity.getQuantity());

    return dto;
  }

  @Override
  public OrderItem toEntity(BillOrderItemDto dto) {
    throw new UnsupportedOperationException("Not supported operation. Entity used only for exposing outside");
  }
}
