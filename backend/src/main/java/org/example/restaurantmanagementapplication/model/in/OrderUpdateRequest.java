package org.example.restaurantmanagementapplication.model.in;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderUpdateRequest {
	@NotEmpty
	private List<OrderItemUpdateRequest> orderedItems;
}
