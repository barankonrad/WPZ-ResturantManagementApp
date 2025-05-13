package org.example.restaurantmanagementapplication.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemForMenuDTO {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
}
