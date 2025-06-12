package org.example.restaurantmanagementapplication.service;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.example.restaurantmanagementapplication.entity.Bill;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.repository.BillRepository;
import org.example.restaurantmanagementapplication.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

  @Mock
  private BillRepository billRepository;

  @Mock
  private OrderRepository orderRepository;

  @InjectMocks
  private BillService billService;

  @Captor
  private ArgumentCaptor<Bill> billCaptor;

  private Order order;
  private Bill bill;

  @BeforeEach
  void setup() {
    order = new Order();
    order.setId(1L);
    OrderItem item1 = new OrderItem();
    item1.setId(1L);
    item1.setOrder(order);
    item1.setQuantity(2);
    item1.setPrice(BigDecimal.valueOf(50.00));
    OrderItem item2 = new OrderItem();
    item2.setId(2L);
    item2.setOrder(order);
    item2.setQuantity(1);
    item2.setPrice(BigDecimal.valueOf(50.00));
    order.setItems(List.of(item1, item2));

    bill = new Bill();
    bill.setId(1L);
    bill.setOrder(order);
    bill.setGeneratedAt(Instant.now());
    bill.setTotalPrice(BigDecimal.valueOf(150).setScale(2));
  }

  @Test
  void generateBill_shouldCreateAndSaveBill() {
    // given
    when(orderRepository.findById(1)).thenReturn(Optional.of(order));

    // when
    billService.generateBillByOrderId(1);

    // then
    verify(orderRepository).findById(1);
    verify(billRepository).save(billCaptor.capture());
    Bill savedBill = billCaptor.getValue();
    assertEquals(order, savedBill.getOrder());
    assertNotNull(savedBill.getGeneratedAt());
    assertEquals(BigDecimal.valueOf(150).setScale(2), savedBill.getTotalPrice());
  }

  @Test
  void findById_shouldReturnBill_whenBillExists() {
    // given
    when(billRepository.findAll()).thenReturn(List.of(bill));

    // when
    Bill result = billService.getBillByOrderId(1);

    // then
    assertNotNull(result);
    assertEquals(1L, result.getId());
    verify(billRepository).findAll();
  }

  @Test
  void findById_shouldReturnNull_whenBillDoesNotExist() {
    // given
    when(billRepository.findAll()).thenReturn(emptyList());

    // when
    Bill result = billService.getBillByOrderId(1);

    // then
    assertNull(result);
    verify(billRepository).findAll();
  }
}