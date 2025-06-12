package org.example.restaurantmanagementapplication.service;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.Bill;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.repository.BillRepository;
import org.example.restaurantmanagementapplication.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BillService {

  private final BillRepository billRepository;
  private final OrderRepository orderRepository;

  @Transactional(readOnly = true)
  public Bill getBillByOrderId(Integer orderId) {
    return billRepository.findAll()
        .stream()
        .filter(bill -> bill.getOrder().getId().equals(orderId.longValue()))
        .findAny()
        .orElse(null);
  }

  public Bill generateBillByOrderId(Integer orderId) {
    var order = orderRepository.findById(Math.toIntExact(orderId))
        .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

    return generateBill(order);

  }

  private Bill generateBill(Order order) {
    if (order == null) {
      throw new IllegalArgumentException("Order cannot be null");
    }

    if (order.getItems() == null || order.getItems().isEmpty()) {
      throw new IllegalArgumentException("Order must have items to generate a bill");
    }

    BigDecimal totalPrice = order.getItems()
        .stream()
        .map(this::calculateOrderItemPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    var bill = new Bill();
    bill.setOrder(order);
    bill.setGeneratedAt(Instant.now());
    bill.setTotalPrice(totalPrice);

    return billRepository.save(bill);
  }

  private BigDecimal calculateOrderItemPrice(OrderItem orderItem) {
    var itemPrice = orderItem.getPrice();
    var quantity = orderItem.getQuantity();
    return itemPrice.multiply(BigDecimal.valueOf(quantity)).setScale(2, HALF_UP);
  }
}