package org.example.restaurantmanagementapplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

  @Mock private MenuItemRepository menuItemRepository;

  @InjectMocks private MenuItemService menuItemService;

  private MenuItem menuItem;

  @BeforeEach
  void setup() {
    menuItem = new MenuItem();
    menuItem.setId(1L);
    menuItem.setName("Pizza");
    menuItem.setDescription("Delicious cheese pizza");
    menuItem.setPrice(BigDecimal.valueOf(15.99));
    menuItem.setAvailable(true);
  }

  @Test
  void findAll_shouldReturnAllMenuItems() {
    // given
    when(menuItemRepository.findAll()).thenReturn(List.of(menuItem));

    // when
    var result = menuItemService.findAll();

    // then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Pizza", result.getFirst().getName());
    verify(menuItemRepository).findAll();
  }

  @Test
  void save_shouldPersistMenuItem() {
    // given
    when(menuItemRepository.save(menuItem)).thenReturn(menuItem);

    // when
    var result = menuItemService.save(menuItem);

    // then
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Pizza", result.getName());
    verify(menuItemRepository).save(menuItem);
  }
}
