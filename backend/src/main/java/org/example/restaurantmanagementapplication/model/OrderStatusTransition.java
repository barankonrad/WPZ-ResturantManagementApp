package org.example.restaurantmanagementapplication.model;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderStatusTransition {

  private static final EnumMap<OrderStatus, Set<OrderStatus>> allowedTransitions =
          new EnumMap<>(OrderStatus.class);

  static {
    allowedTransitions.put(OrderStatus.NEW, EnumSet.of(OrderStatus.PENDING, OrderStatus.CANCELLED));
    allowedTransitions.put(
            OrderStatus.PENDING, EnumSet.of(OrderStatus.CONFIRMED, OrderStatus.CANCELLED));
    allowedTransitions.put(OrderStatus.CONFIRMED, EnumSet.of(OrderStatus.IN_PROGRESS));
    allowedTransitions.put(OrderStatus.IN_PROGRESS, EnumSet.of(OrderStatus.READY));
    allowedTransitions.put(OrderStatus.READY, EnumSet.of(OrderStatus.COMPLETED));
    allowedTransitions.put(OrderStatus.COMPLETED, EnumSet.noneOf(OrderStatus.class));
    allowedTransitions.put(OrderStatus.CANCELLED, EnumSet.noneOf(OrderStatus.class));
  }

  public static boolean canTransition(OrderStatus from, OrderStatus to) {
    return allowedTransitions.getOrDefault(from, EnumSet.noneOf(OrderStatus.class)).contains(to);
  }

  public static boolean isCancellable(OrderStatus status) {
    return status == OrderStatus.NEW || status == OrderStatus.PENDING;
  }

  public static boolean canBeBilled(OrderStatus status) {
    return status == OrderStatus.COMPLETED;
  }

  public static boolean canBeUpdated(OrderStatus status) {
    return status == OrderStatus.NEW || status == OrderStatus.PENDING;
  }
}
