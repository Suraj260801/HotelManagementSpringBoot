package com.example.demo.exception_handler.food_exceptions;

public class FoodNotFoundException extends  RuntimeException{
    public FoodNotFoundException(String message){
        super(message);
    }

}
