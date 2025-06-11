package org.example.restaurantmanagementapplication.model.in;

import lombok.Data;

@Data
public class OrderItemUpdateRequest {
	private Long menuItemId;
	private int quantity;
}
