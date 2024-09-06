package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Room;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Room} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for Room entities.
 * </p>
 */
public interface RoomRepository extends JpaRepository<Room,Long> {

    /**
     * Retrieves a {@link Room} entity by its type.
     *
     * @param type the type of the room.
     * @return an {@link Optional} containing the {@link Room} entity if found, otherwise empty.
     */
     Optional<Room> getRoomByType(String type);
    
}
