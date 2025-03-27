package org.example.restaurantmanagementapplication.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

	@Autowired
	public CustomUserRepositoryImpl(EntityManager entityManager) {
	}

}
