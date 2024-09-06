package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Complaint;

/**
 * Repository interface for managing {@link Complaint} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for Complaint entities.
 * </p>
 */
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    
}
