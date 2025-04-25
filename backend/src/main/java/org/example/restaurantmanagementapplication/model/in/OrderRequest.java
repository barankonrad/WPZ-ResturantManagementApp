package org.example.restaurantmanagementapplication.model.in;

import java.util.List;
import lombok.Data;

@Data
public class OrderRequest {
  private List<OrderItemRequest> orderedItems;
  private String createdBy;
}
