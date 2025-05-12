package org.example.restaurantmanagementapplication.rest;

import org.example.restaurantmanagementapplication.service.MenuItemService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MenuItemControllerTestConfig {

  @Bean
  public MenuItemService menuItemService() {
    return Mockito.mock(MenuItemService.class);
  }
}
