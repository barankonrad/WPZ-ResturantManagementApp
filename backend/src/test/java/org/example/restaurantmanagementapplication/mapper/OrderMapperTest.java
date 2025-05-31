package org.example.restaurantmanagementapplication.mapper;

import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.model.OrderStatus;
import org.example.restaurantmanagementapplication.model.out.OrderDto;
import org.example.restaurantmanagementapplication.model.out.OrderItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    @Mock
    private OrderItemMapper orderItemMapper;

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapper(orderItemMapper);
    }

    @Test
    void toDTO_shouldReturnNull_whenEntityIsNull() {
        assertThat(orderMapper.toDTO(null)).isNull();
    }

    @Test
    void toDTO_shouldMapAllFields_whenEntityIsNotNull() {
        // given
        var order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);
        var now = Instant.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        
        var orderItem = new OrderItem();
        var orderItemDto = new OrderItemDto(null, 1, null);
        order.setItems(List.of(orderItem));
        
        when(orderItemMapper.toDTO(orderItem)).thenReturn(orderItemDto);
        
        // when
        OrderDto result = orderMapper.toDTO(order);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.getCreatedAt()).isEqualTo(now);
        assertThat(result.getUpdatedAt()).isEqualTo(now);
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getItems().getFirst()).isEqualTo(orderItemDto);
    }
    
    @Test
    void toDTO_shouldReturnEmptyItemsList_whenEntityItemsAreNull() {
        // given
        var order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);
        var now = Instant.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        order.setItems(null);
        
        // when
        OrderDto result = orderMapper.toDTO(order);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getItems()).isEmpty();
    }

    @Test
    void toEntity_shouldReturnNull_whenDtoIsNull() {
        assertThat(orderMapper.toEntity(null)).isNull();
    }

    @Test
    void toEntity_shouldMapAllFields_whenDtoIsNotNull() {
        // given
        var orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setStatus(OrderStatus.CONFIRMED);
        var now = Instant.now();
        orderDto.setCreatedAt(now);
        orderDto.setUpdatedAt(now);
        orderDto.setItems(Collections.emptyList());
        
        // when
        Order result = orderMapper.toEntity(orderDto);
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
        assertThat(result.getCreatedAt()).isEqualTo(now);
        assertThat(result.getUpdatedAt()).isEqualTo(now);
    }
}