package org.example.restaurantmanagementapplication.repository;

import org.example.restaurantmanagementapplication.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
