package org.example.restaurantmanagementapplication.rest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.common.SessionManager;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.mapper.OrderMapper;
import org.example.restaurantmanagementapplication.model.OrderStatus;
import org.example.restaurantmanagementapplication.model.OrderStatusTransition;
import org.example.restaurantmanagementapplication.model.in.OrderItemRequest;
import org.example.restaurantmanagementapplication.model.in.OrderRequest;
import org.example.restaurantmanagementapplication.model.in.OrderUpdateRequest;
import org.example.restaurantmanagementapplication.model.out.OrderDto;
import org.example.restaurantmanagementapplication.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final OrderMapper mapper;
  private final SessionManager sessionManager;

  @GetMapping
  public ResponseEntity<List<OrderDto>> retrieveAllOrders() {
    return ResponseEntity.ok(orderService.findAll().stream().map(mapper::toDTO).toList());
  }

  @PostMapping
  public ResponseEntity<OrderDto> createOrder(@RequestBody List<OrderItemRequest> items) {
    var orderRequest = new OrderRequest();
    orderRequest.setOrderedItems(items);
    orderRequest.setCreatedBy(getCurrentUserName());
    var order = orderService.createOrder(orderRequest);
    return ResponseEntity.ok(mapper.toDTO(order));
  }

  @PostMapping("{id}/cancel")
  public ResponseEntity<OrderDto> cancelOrder(@PathVariable int id) {
    var order = orderService.findById(id);
    if (order == null) {
      return ResponseEntity.notFound().build();
    }
    if (!OrderStatusTransition.isCancellable(order.getStatus())) {
      return ResponseEntity.badRequest().body(mapper.toDTO(order));
    }
    order.setStatus(OrderStatus.CANCELLED);
    order.setUpdatedBy(getCurrentUserName());
    order.setUpdatedAt(Instant.now());
    orderService.save(order);
    return ResponseEntity.ok(mapper.toDTO(order));
  }

  @PostMapping("{id}/mark-as-pending")
  public ResponseEntity<OrderDto> setPending(@PathVariable int id) {
    return updateStatus(id, OrderStatus.PENDING);
  }

  @PostMapping("{id}/confirm")
  public ResponseEntity<OrderDto> setConfirmed(@PathVariable int id) {
    return updateStatus(id, OrderStatus.CONFIRMED);
  }

  @PostMapping("{id}/start-preparation")
  public ResponseEntity<OrderDto> setInProgress(@PathVariable int id) {
    return updateStatus(id, OrderStatus.IN_PROGRESS);
  }

  @PostMapping("{id}/mark-as-ready")
  public ResponseEntity<OrderDto> setReady(@PathVariable int id) {
    return updateStatus(id, OrderStatus.READY);
  }

  @PostMapping("{id}/complete")
  public ResponseEntity<OrderDto> setCompleted(@PathVariable int id) {
    return updateStatus(id, OrderStatus.COMPLETED);
  }

  @PostMapping("{id}/update")
  public ResponseEntity<OrderDto> updateOrder(@PathVariable int id, @RequestBody @Valid OrderUpdateRequest orderUpdateRequest) {
    var order = orderService.findById(id);
    if (order == null) {
      return ResponseEntity.notFound().build();
    }

    try {
      order =
          orderService.updateOrder(id, orderUpdateRequest.getOrderedItems(), getCurrentUserName());
      return ResponseEntity.ok(mapper.toDTO(order));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.of(
              ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()))
          .build();
    }
  }

  private ResponseEntity<OrderDto> updateStatus(int id, OrderStatus targetStatus) {
    var order = orderService.findById(id);
    if (order == null) {
      return ResponseEntity.notFound().build();
    }
    if (!OrderStatusTransition.canTransition(order.getStatus(), targetStatus)) {
      return ResponseEntity.unprocessableEntity().body(mapper.toDTO(order));
    }
    order.setStatus(targetStatus);
    order.setUpdatedBy(getCurrentUserName());
    order.setUpdatedAt(Instant.now());
    var result = orderService.save(order);
    return ResponseEntity.ok(mapper.toDTO(result));
  }

  private String getCurrentUserName() {
    Optional<User> currentUser = sessionManager.getCurrentUser();
    return currentUser.map(User::getEmail).orElse("anonymous");
  }
}
