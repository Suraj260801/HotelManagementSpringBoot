package com.example.demo.exception_handler.food_exceptions;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class FoodItemNotAvailableException extends  RuntimeException{
    private HashMap<String,Integer> orderedFoodItems;
    private HashMap<String,Integer> availableFoodItems;
    public FoodItemNotAvailableException(String message,HashMap<String,Integer> orderedFoodItems,HashMap<String,Integer> availableFoodItems){
        super(message);
        this.availableFoodItems=availableFoodItems;
        this.orderedFoodItems=orderedFoodItems;
    }
}
