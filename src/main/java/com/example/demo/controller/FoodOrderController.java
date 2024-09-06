package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.response_dto.FoodOrderAcceptedDTO;
import com.example.demo.models.FoodOrder;
import com.example.demo.service.FoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food_order")
public class FoodOrderController {

    // Injects the FoodOrderService to handle business logic related to food orders
     @Autowired
    private FoodOrderService foodOrderService;


    /**
     * Retrieves a list of all food orders.
     * @return ResponseEntity containing the list of all FoodOrder objects.
     */
    @GetMapping
    public ResponseEntity<List<FoodOrderAcceptedDTO>> getAllFoods() {
         List<FoodOrderAcceptedDTO> foods=foodOrderService.findAllFoods();
         return ResponseEntity.ok(foods);
    }


    /**
     * Creates a new food order.
     * @param food The FoodOrder object to be created.
     * @return The created FoodOrder object.
     */
    @PostMapping
    public FoodOrderAcceptedDTO createFood(@RequestBody FoodOrder food) {
        return foodOrderService.saveFood(food);
    }



    /**
     * Retrieves a specific food order by its Customer Name.
     * @param name The Customer Name of the food order to retrieve.
     * @return ResponseEntity containing the FoodOrder object if found.
     */
    @GetMapping("/{name}")
    public ResponseEntity<Object> getFoodOrderByCustomerName(@PathVariable String name) {
       FoodOrderAcceptedDTO foodOrderAcceptedDTO=foodOrderService.findFoodByCustomerName(name);
       return ResponseEntity.ok(foodOrderAcceptedDTO);
    }

    /**
     * Updates an existing food order with a given ID.
     * @param id The ID of the food order to update.
     * @param food The updated FoodOrder object.
     * @return The updated FoodOrder object.
     */
    @PostMapping("/{id}")
    public FoodOrderAcceptedDTO updateFood(@PathVariable Long id, @RequestBody FoodOrder food) {
        food.setId(id);
        return foodOrderService.saveFood(food);
    }

    /**
     * Deletes a food order by its ID.
     * @param id The ID of the food order to delete.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id) {
        return ResponseEntity.ok("Food Order Cancelled Successfully");
    }

}
