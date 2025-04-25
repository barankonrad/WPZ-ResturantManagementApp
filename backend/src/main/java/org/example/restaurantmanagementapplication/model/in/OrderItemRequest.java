package org.example.restaurantmanagementapplication.model.in;

import lombok.Data;

@Data
public class OrderItemRequest {

  private long menuItemId;
  private int quantity;
}
