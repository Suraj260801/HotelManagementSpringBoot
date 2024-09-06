package com.example.demo.exception_handler.food_order_exceptions;

public class InvalidFoodOrderException extends RuntimeException{
    public InvalidFoodOrderException(String message){
        super(message);
    }
}
