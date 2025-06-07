package org.example.restaurantmanagementapplication.repository;

import org.example.restaurantmanagementapplication.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
