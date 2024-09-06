package com.example.demo.repository;

import com.example.demo.models.BookRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Feedback;

import java.util.Optional;

/**
 * Repository interface for managing {@link Feedback} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for Feedback entities.
 * </p>
 */
public interface FeedbackRepository extends JpaRepository<Feedback,Long>{

    /**
     * Retrieves a {@link Feedback} entity by the customer's name.
     *
     * @param name the name of the customer associated with the booked room.
     * @return an {@link Optional} containing the {@link Feedback} if found, or empty if no room is found.
     */
     Optional<Feedback> getFeedbackByName(String name);

    /**
     * Retrieves a {@link Feedback} entity by the customer's name.
     *
     * @param email the name of the customer associated with the booked room.
     * @return an {@link Optional} containing the {@link Feedback} if found, or empty if no room is found.
     */
     Optional<Feedback> getFeedbackByEmail(String email);
}