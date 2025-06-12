package org.example.restaurantmanagementapplication.model.out;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private boolean isAvailable;
  private String imageUrl;
}
