package org.example.restaurantmanagementapplication.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import org.example.restaurantmanagementapplication.entity.Bill;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.model.out.BillDto;
import org.example.restaurantmanagementapplication.model.out.BillOrderItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BillMapperTest {

  @Mock
  private BillOrderItemMapper billOrderItemMapper;

  @InjectMocks
  private BillMapper billMapper;

  private Bill bill;
  private OrderItem orderItem;
  private BillOrderItemDto billOrderItemDto;

  @BeforeEach
  void setUp() {
    MenuItem menuItem = new MenuItem();
    menuItem.setId(1L);
    menuItem.setName("Test Item");
    menuItem.setPrice(BigDecimal.valueOf(10.99));

    orderItem = new OrderItem();
    orderItem.setId(1L);
    orderItem.setMenuItem(menuItem);
    orderItem.setQuantity(2);
    orderItem.setPrice(BigDecimal.valueOf(21.98));

    Order order = new Order();
    order.setId(1L);
    order.setItems(Collections.singletonList(orderItem));

    bill = new Bill();
    bill.setId(1L);
    bill.setOrder(order);
    bill.setGeneratedAt(Instant.now());
    bill.setTotalPrice(BigDecimal.valueOf(21.98));

    billOrderItemDto = new BillOrderItemDto();
    billOrderItemDto.setName("Test Item");
    billOrderItemDto.setQuantity(2);
    billOrderItemDto.setPrice(BigDecimal.valueOf(21.98));
  }

  @Test
  void testToDtoWithValidBill() {
    when(billOrderItemMapper.toDTO(orderItem)).thenReturn(billOrderItemDto);

    BillDto result = billMapper.toDTO(bill);

    assertNotNull(result);
    assertEquals(bill.getId(), result.getId());
    assertEquals(LocalDateTime.ofInstant(bill.getGeneratedAt(), ZoneId.systemDefault()),
        result.getGenerationDateTime());
    assertEquals(bill.getTotalPrice(), result.getTotalPrice());
    assertEquals(1, result.getItems().size());
    assertEquals(billOrderItemDto, result.getItems().getFirst());

    verify(billOrderItemMapper).toDTO(orderItem);
  }

  @Test
  void testToDtoWithNullBill() {
    BillDto result = billMapper.toDTO(null);

    assertNull(result);

    verifyNoInteractions(billOrderItemMapper);
  }
}
