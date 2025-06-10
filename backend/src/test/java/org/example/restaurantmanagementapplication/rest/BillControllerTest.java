package org.example.restaurantmanagementapplication.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.example.restaurantmanagementapplication.model.in.OrderItemRequest;
import org.example.restaurantmanagementapplication.model.out.BillDto;
import org.example.restaurantmanagementapplication.model.out.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BillControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  private Integer completedOrderId;
  private Long generatedBillId;

  @BeforeEach
  void setupCompletedOrder() {
    // Create order
    List<OrderItemRequest> orderItems = new ArrayList<>();
    OrderItemRequest item = new OrderItemRequest();
    item.setMenuItemId(1L);
    item.setQuantity(2);
    orderItems.add(item);

    ResponseEntity<OrderDto> createResponse = restTemplate.postForEntity(
        "/orders", orderItems, OrderDto.class
    );
    assertEquals(HttpStatus.OK, createResponse.getStatusCode());

    int orderId = createResponse.getBody().getId().intValue();

    // Complete order
    restTemplate.postForEntity("/orders/" + orderId + "/mark-as-pending", null, OrderDto.class);
    restTemplate.postForEntity("/orders/" + orderId + "/confirm", null, OrderDto.class);
    restTemplate.postForEntity("/orders/" + orderId + "/start-preparation", null, OrderDto.class);
    restTemplate.postForEntity("/orders/" + orderId + "/mark-as-ready", null, OrderDto.class);
    restTemplate.postForEntity("/orders/" + orderId + "/complete", null, OrderDto.class);

    this.completedOrderId = orderId;

    // Generate bill
    ResponseEntity<BillDto> billResponse = restTemplate.postForEntity(
        "/bill/" + orderId, null, BillDto.class
    );
    assertEquals(HttpStatus.OK, billResponse.getStatusCode());

    this.generatedBillId = billResponse.getBody().getId();
  }

  @Test
  void testGetBill() {
    // When
    ResponseEntity<BillDto> response = restTemplate.getForEntity(
        "/bill/" + completedOrderId, BillDto.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(generatedBillId, response.getBody().getId());
    assertNotNull(response.getBody().getItems());
    assertNotNull(response.getBody().getTotalPrice());
    assertNotNull(response.getBody().getGenerationDateTime());
  }

  @Test
  void testGenerateBillForNonExistentOrder() {
    // When
    ResponseEntity<BillDto> response = restTemplate.postForEntity(
        "/bill/999999", null, BillDto.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testGenerateBillForOrderWithInvalidStatus() {
    // Given
    List<OrderItemRequest> orderItems = new ArrayList<>();
    OrderItemRequest item = new OrderItemRequest();
    item.setMenuItemId(1L);
    item.setQuantity(1);
    orderItems.add(item);

    ResponseEntity<OrderDto> response = restTemplate.postForEntity(
        "/orders", orderItems, OrderDto.class
    );
    int orderId = response.getBody().getId().intValue();

    // When
    ResponseEntity<BillDto> billResponse = restTemplate.postForEntity(
        "/bill/" + orderId, null, BillDto.class
    );

    // Then
    assertEquals(HttpStatus.NOT_ACCEPTABLE, billResponse.getStatusCode());
  }

  @Test
  void testGenerateBillForOrderWithExistingBill() {
    // When
    ResponseEntity<BillDto> response = restTemplate.postForEntity(
        "/bill/" + completedOrderId, null, BillDto.class
    );

    // Then
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  void testGetBillForNonExistentOrder() {
    // When
    ResponseEntity<BillDto> response = restTemplate.getForEntity(
        "/bill/999999", BillDto.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void testGetBillForOrderWithoutBill() {
    // Given
    List<OrderItemRequest> orderItems = new ArrayList<>();
    OrderItemRequest item = new OrderItemRequest();
    item.setMenuItemId(1L);
    item.setQuantity(1);
    orderItems.add(item);

    ResponseEntity<OrderDto> orderResponse = restTemplate.postForEntity(
        "/orders", orderItems, OrderDto.class
    );
    int orderId = orderResponse.getBody().getId().intValue();

    ResponseEntity<BillDto> response = restTemplate.getForEntity(
        "/bill/" + orderId, BillDto.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}