package org.example.restaurantmanagementapplication.rest;

import org.example.restaurantmanagementapplication.mapper.MenuMapper;
import org.example.restaurantmanagementapplication.service.MenuItemService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MenuControllerTestConfig {
  @Bean
  public MenuItemService menuItemService() {
    return Mockito.mock(MenuItemService.class);
  }

  @Bean
  public MenuMapper menuMapper() {
    return Mockito.mock(MenuMapper.class);
  }
}
