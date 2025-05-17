package org.example.restaurantmanagementapplication.mapper;

import java.util.Collections;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.model.out.OrderDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper implements GenericMapper<Order, OrderDto> {

  private final OrderItemMapper orderItemMapper;

  @Override
  public OrderDto toDTO(Order entity) {
    if (entity == null) {
      return null;
    }

    var items = Objects.requireNonNullElse(entity.getItems(), Collections.emptyList())
        .stream()
        .map(item -> orderItemMapper.toDTO((OrderItem) item))
        .toList();

    return new OrderDto(
        entity.getId(),
        entity.getStatus(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        items
    );
  }

  @Override
  public Order toEntity(OrderDto dto) {
    if (dto == null) {
      return null;
    }

    Order order = new Order();
    order.setId(dto.getId());
    order.setStatus(dto.getStatus());
    order.setCreatedAt(dto.getCreatedAt());
    order.setUpdatedAt(dto.getUpdatedAt());

    return order;
  }
}