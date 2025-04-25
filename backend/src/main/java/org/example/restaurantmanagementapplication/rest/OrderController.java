package org.example.restaurantmanagementapplication.rest;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.common.SessionManager;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.model.in.OrderItemRequest;
import org.example.restaurantmanagementapplication.model.in.OrderRequest;
import org.example.restaurantmanagementapplication.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final SessionManager sessionManager;

  @GetMapping
  public ResponseEntity<List<Order>> retrieveAllOrders() {
    return ResponseEntity.ok(orderService.findAll());
  }

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody List<OrderItemRequest> items) {
    Optional<User> currentUser = sessionManager.getCurrentUser();

    var createdBy = currentUser.isPresent()
        ? currentUser.get().getEmail()
        : "anonymous";

    var orderRequest = new OrderRequest();
    orderRequest.setOrderedItems(items);
    orderRequest.setCreatedBy(createdBy);
    Order order = orderService.createOrder(orderRequest);
    return ResponseEntity.ok(order);
  }
}