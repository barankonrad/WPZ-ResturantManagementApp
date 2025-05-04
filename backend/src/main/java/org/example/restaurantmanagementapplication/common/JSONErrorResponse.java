package org.example.restaurantmanagementapplication.common;

import lombok.Getter;
import lombok.Setter;
/*
TODO: please use record
 */
@Setter
@Getter
public class JSONErrorResponse {
	private String message;

	public JSONErrorResponse(String message) {
		this.message = message;
	}

}
