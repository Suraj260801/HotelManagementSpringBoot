package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class BookRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Unique identifier for the booking.
    private Long id;
    // Name of the customer who made the booking.
    private String customerName;
    // The date and time when the booking was made.
    private LocalDateTime bookingDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="booking",
            joinColumns = @JoinColumn(name="bookroom_id"),
            inverseJoinColumns = @JoinColumn(name="room_id")
    )
    // The room associated with this booking.
    private Set<Room> rooms;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="room_bill_id")
    // The bill associated with this booking.
    private Bill bill;

    /**
     * Constructs a new BookRoom with the specified details and sets the booking date to the current time.
     *
     * @param customerName The name of the customer making the booking.
     * @param rooms The room being booked.
     * @param bill The bill associated with this booking.
     */
    public BookRoom(String customerName, Set<Room> rooms, Bill bill) {
        this.customerName = customerName;
        this.bookingDate = LocalDateTime.now();
        this.rooms=rooms;
        this.bill=bill;
    }

}
