package com.example.demo.models;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Bill {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Unique identifier for the bill.
    private Long id;
    // The date and time when the bill was created.
    private LocalDateTime date;
    // The total amount for the bill.
    private double totalAmount;

    @OneToOne(mappedBy = "bill")
    // Prevents infinite recursion during JSON serialization.
    @JsonBackReference
    // The associated food order for which this bill was created.
    private FoodOrder foodOrder;


    /**
     * Constructs a new Bill with the specified total amount and sets the date to the current time.
     *
     * @param totalAmount The total amount of the bill.
     */
    public Bill(double totalAmount) {
        this.date = LocalDateTime.now();
        this.totalAmount = totalAmount;
    }

}
