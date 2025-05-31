package org.example.restaurantmanagementapplication.model.out;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

  private MenuItemDTO menuItem;
  private int quantity;
  private BigDecimal price;
}
