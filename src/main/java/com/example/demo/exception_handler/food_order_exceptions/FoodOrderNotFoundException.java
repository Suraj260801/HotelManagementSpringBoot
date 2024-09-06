package com.example.demo.exception_handler.food_order_exceptions;

public class FoodOrderNotFoundException extends  RuntimeException{

    public FoodOrderNotFoundException(String message){
        super(message);
    }
}
