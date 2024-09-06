package com.example.demo.exception_handler.menu_exceptions;

public class MenuNotFoundException extends RuntimeException{
    public MenuNotFoundException(String message){
        super(message);
    }
}
