package org.example.restaurantmanagementapplication.rest;

import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.mapper.BillMapper;
import org.example.restaurantmanagementapplication.model.out.BillDto;
import org.example.restaurantmanagementapplication.service.BillService;
import org.example.restaurantmanagementapplication.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {

  private final BillMapper billMapper;
  private final BillService billService;
  private final OrderService orderService;

  @RequestMapping("/{orderId}")
  public ResponseEntity<BillDto> getBill(@PathVariable Integer orderId) {
    var order = orderService.findById(orderId);
    if (order == null) {
      return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
          "Order with ID %s not found".formatted(orderId))).build();
    }

    var bill = billService.getBillByOrderId(orderId);
    if (bill == null) {
      return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
          "Bill for order ID %s not found".formatted(orderId))).build();
    }

    return ResponseEntity.ok(billMapper.toDTO(bill));
  }

  @PostMapping("/{orderId}")
  public ResponseEntity<BillDto> generateBillForOrder(@PathVariable Integer orderId) {
    var order = orderService.findById(orderId);
    if (order == null) {
      return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
          "Order with ID %s not found".formatted(orderId))).build();
    }

    var existingBill = billService.getBillByOrderId(orderId);
    if (existingBill != null) {
      return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
          "Bill for order ID %s already exists".formatted(orderId))).build();
    }

    var generatedBill = billService.generateBillByOrderId(orderId);
    return ResponseEntity.ok(billMapper.toDTO(generatedBill));
  }
}
