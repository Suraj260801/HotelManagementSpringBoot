package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Unique identifier for the food order.
    private Long id;
    // Name of the customer who made the order.
    private String customerName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_order_id")
    // List of food items included in the order.
    private List<Food> foodItems;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bill_id")
    @JsonManagedReference
    // Bill associated with the food order.
    private Bill bill;

    /**
     * Constructs a new FoodOrder with the specified details.
     *
     * @param foodItems The list of food items included in the order.
     * @param customerName The name of the customer who made the order.
     * @param bill The bill associated with the food order.
     */
    public FoodOrder( List<Food> foodItems,String customerName,Bill bill) {
        this.foodItems = foodItems;
        this.customerName=customerName;
        this.bill=bill;
    }

}
