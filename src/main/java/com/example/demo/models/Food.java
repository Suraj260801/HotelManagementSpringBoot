package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Unique identifier for the food item.
    private Long id;
    // Quantity of the food item ordered.
    private Long quantity;
    // Name of the food item.
    private String foodName;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    // The menu associated with the food item.
    private Menu menu;

    /**
     * Constructs a new Food item with the specified details.
     *
     * @param menu The menu associated with the food item.
     * @param foodName The name of the food item.
     * @param quantity The quantity of the food item.
     */
    public Food(Menu menu, String foodName, Long quantity){
        this.menu=menu;
        this.foodName=foodName;
        this.quantity=quantity;
    }

}
