package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Suggestion;

/**
 * Repository interface for managing {@link Suggestion} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for Suggestion entities.
 * </p>
 */
public interface SuggestionRepository extends JpaRepository<Suggestion,Long> {
    
}
