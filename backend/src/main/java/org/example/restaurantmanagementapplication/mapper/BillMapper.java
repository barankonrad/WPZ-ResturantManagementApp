package org.example.restaurantmanagementapplication.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.Bill;
import org.example.restaurantmanagementapplication.model.out.BillDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillMapper implements GenericMapper<Bill, BillDto> {

  private final BillOrderItemMapper billOrderItemMapper;

  @Override
  public BillDto toDTO(Bill entity) {
    if (entity == null) {
      return null;
    }

    var localDateTime = LocalDateTime.ofInstant(entity.getGeneratedAt(), ZoneId.systemDefault());
    var orderItems = entity.getOrder().getItems().stream()
        .map(billOrderItemMapper::toDTO)
        .toList();

    return new BillDto(
        entity.getId(),
        localDateTime,
        orderItems,
        entity.getTotalPrice()
    );
  }

  @Override
  public Bill toEntity(BillDto dto) {
    throw new UnsupportedOperationException("Not supported operation. Entity used only for exposing outside");
  }
}
