package com.example.demo.service;

import com.example.demo.exception_handler.food_exceptions.FoodNotFoundException;
import com.example.demo.models.Food;
import com.example.demo.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    // Repository to handle CRUD operations on Food entities.
    @Autowired
    private FoodRepository foodRepository;

    /**
     * Retrieves a food item by its ID.
     * Throws an exception if the ID is invalid or the food item is not found.
     *
     * @param id The ID of the food item to retrieve.
     * @return The food item associated with the given ID.
     * @throws FoodNotFoundException If the food item is not found or the ID is invalid.
     */
    public Food getFoodById(Long id){
        if(id<0){
           throw new FoodNotFoundException("Invalid Food Id, Please Enter Valid Food Id "+id);
        }
        Optional<Food> food=foodRepository.findById(id);
        return food.orElseThrow(()->new FoodNotFoundException("No Food Found with Id "+id));
    }

    /**
     * Retrieves all food items from the repository.
     *
     * @return A list of all food items.
     */
    public List<Food> getAllFoods(){
        List<Food> foods=foodRepository.findAll();
        if(foods.isEmpty()){
            throw new FoodNotFoundException("No Foods Found");
        }
        return foodRepository.findAll();
    }
}
