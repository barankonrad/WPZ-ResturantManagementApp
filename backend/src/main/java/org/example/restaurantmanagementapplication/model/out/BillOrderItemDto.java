package org.example.restaurantmanagementapplication.model.out;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillOrderItemDto {

  private String name;
  private int quantity;
  private BigDecimal price;
}
