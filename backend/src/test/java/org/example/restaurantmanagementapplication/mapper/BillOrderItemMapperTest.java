package org.example.restaurantmanagementapplication.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.model.out.BillOrderItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BillOrderItemMapperTest {

  private BillOrderItemMapper billOrderItemMapper;
  private OrderItem orderItem;
  private MenuItem menuItem;

  @BeforeEach
  void setUp() {
    billOrderItemMapper = new BillOrderItemMapper();

    menuItem = new MenuItem();
    menuItem.setId(1L);
    menuItem.setName("Test Item");
    menuItem.setPrice(BigDecimal.valueOf(10.99));
    menuItem.setDescription("Test Description");

    Order order = new Order();
    order.setId(1L);

    orderItem = new OrderItem();
    orderItem.setId(1L);
    orderItem.setOrder(order);
    orderItem.setMenuItem(menuItem);
    orderItem.setQuantity(2);
    orderItem.setPrice(BigDecimal.valueOf(21.98));
  }

  @Test
  void testToDtoWithValidOrderItem() {
    BillOrderItemDto result = billOrderItemMapper.toDTO(orderItem);

    assertNotNull(result);
    assertEquals(menuItem.getName(), result.getName());
    assertEquals(orderItem.getQuantity(), result.getQuantity());
    assertEquals(orderItem.getPrice(), result.getPrice());
  }

  @Test
  void testToDtoWithNullOrderItem() {
    BillOrderItemDto result = billOrderItemMapper.toDTO(null);

    assertNull(result);
  }
}
