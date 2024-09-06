package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@Getter
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Unique identifier for the menu item.
    private Long id;
    // Name of the menu item.
    private String itemName;
    // Price of the menu item.
    private Integer price;
    // Availability status of the menu item (true if available, false otherwise).
    private Boolean available;
    // Availability count of the menu item(if counts is < unavailable)
    private Integer availableCount;

    /**
     * Constructs a new Menu with the specified details.
     *
     * @param itemName The name of the menu item.
     * @param price The price of the menu item.
     * @param available The availability status of the menu item.
     */
    public Menu( String itemName, Integer price,Boolean available,Integer availableCount) {
        this.itemName = itemName;
        this.price = price;
        this.available=available;
        this.availableCount=availableCount;

    }
}

