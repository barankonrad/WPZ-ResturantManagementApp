package org.example.restaurantmanagementapplication.service;
/*
TODO: please rearrange package structure in more business-oriented way/
 */
import java.util.List;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.model.in.OrderRequest;

public interface OrderService {

  List<Order> findAll();

  Order createOrder(OrderRequest orderRequest);

}
