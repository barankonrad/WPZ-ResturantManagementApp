package org.example.restaurantmanagementapplication.model.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {

  private Long id;
  private LocalDateTime generationDateTime;
  private List<BillOrderItemDto> items;
  private BigDecimal totalPrice;
}
