package org.example.restaurantmanagementapplication.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.model.out.MenuItemDTO;
import org.example.restaurantmanagementapplication.model.out.OrderItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class OrderItemMapperTest {

  @Mock
  private MenuItemMapper menuItemMapper;

  private OrderItemMapper orderItemMapper;

  @BeforeEach
  void setUp() {
    orderItemMapper = new OrderItemMapper(menuItemMapper);
  }

  @Test
  void toDTO_shouldReturnNull_whenEntityIsNull() {
    assertThat(orderItemMapper.toDTO(null)).isNull();
  }

  @Test
  void toDTO_shouldMapAllFields_whenEntityIsNotNull() {
    var menuItem = new MenuItem();
    var menuItemDto = new MenuItemDTO(1L, "name", "description", BigDecimal.valueOf(10.0), true);
    var orderItem = new OrderItem();
    orderItem.setMenuItem(menuItem);
    orderItem.setQuantity(2);
    orderItem.setPrice(BigDecimal.valueOf(20.0));

    when(menuItemMapper.toDTO(menuItem)).thenReturn(menuItemDto);

    OrderItemDto result = orderItemMapper.toDTO(orderItem);

    assertThat(result).isNotNull();
    assertThat(result.getMenuItem()).isEqualTo(menuItemDto);
    assertThat(result.getQuantity()).isEqualTo(2);
    assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(20.0));
  }

  @Test
  void toEntity_shouldReturnNull_whenDtoIsNull() {
    assertThat(orderItemMapper.toEntity(null)).isNull();
  }

  @Test
  void toEntity_shouldMapAllFields_whenDtoIsNotNull() {
    var menuItemDto = new MenuItemDTO(1L, "name", "description", BigDecimal.valueOf(10.0), true);
    var menuItem = new MenuItem();
    var orderItemDto = new OrderItemDto(menuItemDto, 2, BigDecimal.valueOf(20.0));

    when(menuItemMapper.toEntity(menuItemDto)).thenReturn(menuItem);

    OrderItem result = orderItemMapper.toEntity(orderItemDto);

    assertThat(result).isNotNull();
    assertThat(result.getMenuItem()).isEqualTo(menuItem);
    assertThat(result.getQuantity()).isEqualTo(2);
    assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(20.0));
  }
}