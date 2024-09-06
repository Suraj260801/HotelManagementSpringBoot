package com.example.demo.controller;

import com.example.demo.models.Food;

import com.example.demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/foods")
public class FoodController {


    // Injects the FoodService to handle business logic related to Food entities
    @Autowired
    private FoodService foodService;


    /**
     * Endpoint to get a food item by its ID.
     * @param id The ID of the food item.
     * @return ResponseEntity containing the Food object if found, or an appropriate HTTP status if not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id){
        Food food=foodService.getFoodById(id);
        return ResponseEntity.ok(food);
    }


    /**
     * Endpoint to retrieve all food items.
     * @return ResponseEntity containing a list of all Food objects.
     */
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods(){
        List<Food> orders=foodService.getAllFoods();
        return ResponseEntity.ok(orders);
    }

}