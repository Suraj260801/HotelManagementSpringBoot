package com.example.demo.repository;

import com.example.demo.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for managing {@link Food} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for Food entities.
 * </p>
 */
public interface FoodRepository extends JpaRepository<Food,Long> {
}
