package org.example.restaurantmanagementapplication.model.out;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.restaurantmanagementapplication.model.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

  private Long id;
  private OrderStatus status;
  private Instant createdAt;
  private Instant updatedAt;
  private List<OrderItemDto> items;
}
