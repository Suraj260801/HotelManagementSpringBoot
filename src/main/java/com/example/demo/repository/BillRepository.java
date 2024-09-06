package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Bill;

/**
 * Repository interface for managing {@link Bill} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for Bill entities.
 * </p>
 */
public interface BillRepository extends JpaRepository<Bill,Long> {
    
}
