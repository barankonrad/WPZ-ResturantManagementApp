package org.example.restaurantmanagementapplication.rest;

import org.example.restaurantmanagementapplication.common.SessionManager;
import org.example.restaurantmanagementapplication.mapper.OrderMapper;
import org.example.restaurantmanagementapplication.service.OrderService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class OrderControllerTestConfig {

  @Bean
  public OrderService orderService() {
    return Mockito.mock(OrderService.class);
  }

  @Bean
  public SessionManager sessionManager() {
    return Mockito.mock(SessionManager.class);
  }

  @Bean
  public OrderMapper orderMapper() {
    return Mockito.mock(OrderMapper.class);
  }
}
