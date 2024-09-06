package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Setter
@Entity
@Getter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Unique identifier for the room.
    private Long id;
    // Type or category of the room (e.g., single, double, suite).
    private String type;
    // Availability status of the room (true if available, false otherwise).
    private Boolean available;
    // Price of the room.
    private Integer price;
    // Availability count of the room of given type
    private Integer availableCount;

    // Mapped to rooms reference in BookRoom Entity to establish bidirectional relationship
    //between room and BookRoom.
    @ManyToMany(mappedBy = "rooms")
    @JsonBackReference
    private Set<BookRoom> bookRooms;


    /**
     * Constructs a new Room with the specified details.
     *
     * @param id The unique identifier for the room.
     * @param type The type or category of the room.
     * @param available The availability status of the room.
     * @param price The price of the room.
     */
    public Room(Long id,String type, boolean available, Integer price,Integer availableCount) {
        this.id=id;
        this.type = type;
        this.available = available;
        this.price = price;
        this.availableCount=availableCount;
    }
}
