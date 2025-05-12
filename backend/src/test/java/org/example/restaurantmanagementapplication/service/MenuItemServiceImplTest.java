package org.example.restaurantmanagementapplication.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceImplTest {

  @Mock private MenuItemRepository menuItemRepository;

  @InjectMocks private MenuItemServiceImpl menuItemService;

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
  void findAvailable_shouldReturnAvailableMenuItems() {
    // given
    when(menuItemRepository.findByIsAvailableTrue()).thenReturn(List.of(menuItem));

    // when
    var result = menuItemService.findAvailable();

    // then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertTrue(result.getFirst().isAvailable());
    verify(menuItemRepository).findByIsAvailableTrue();
  }

  @Test
  void findById_shouldReturnMenuItem_whenExists() {
    // given
    when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

    // when
    var result = menuItemService.findById(1L);

    // then
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Pizza", result.getName());
    verify(menuItemRepository).findById(1L);
  }

  @Test
  void findById_shouldReturnNull_whenNotExists() {
    // given
    when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    var result = menuItemService.findById(1L);

    // then
    assertNull(result);
    verify(menuItemRepository).findById(1L);
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

  @Test
  void deleteById_shouldDeleteMenuItem() {
    // when
    menuItemService.deleteById(1L);

    // then
    verify(menuItemRepository).deleteById(1L);
  }
}
