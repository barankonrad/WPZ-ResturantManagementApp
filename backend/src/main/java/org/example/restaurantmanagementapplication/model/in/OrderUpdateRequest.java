package org.example.restaurantmanagementapplication.model.in;

import lombok.Data;

import java.util.List;

@Data
public class OrderUpdateRequest {
	private List<OrderItemUpdateRequest> orderedItems;
}
