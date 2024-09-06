package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.FoodOrder;

import java.util.Optional;

/**
 * Repository interface for managing {@link FoodOrder} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for FoodOrder entities.
 * Includes a custom method to find a FoodOrder by the customer's name.
 * </p>
 */
public interface FoodOrderRepository extends JpaRepository<FoodOrder,Long> {

    /**
     * Finds a FoodOrder by the customer's name.
     *
     * @param customerName the name of the customer associated with the FoodOrder
     * @return an {@link Optional} containing the FoodOrder if found, otherwise empty
     */
     Optional<FoodOrder> getFoodOrderByCustomerName(String customerName);
    
}
