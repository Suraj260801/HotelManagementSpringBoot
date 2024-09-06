package com.example.demo.repository;

import com.example.demo.models.BookRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link BookRoom} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for BookRoom entities.
 * Includes a custom method for retrieving a booked room by customer name.
 * </p>
 */
public interface BookRoomRepository extends JpaRepository<BookRoom,Long> {

    /**
     * Retrieves a {@link BookRoom} entity by the customer's name.
     *
     * @param name the name of the customer associated with the booked room.
     * @return an {@link Optional} containing the {@link BookRoom} if found, or empty if no room is found.
     */
    Optional<BookRoom> getBookRoomByCustomerName(String name);
}
