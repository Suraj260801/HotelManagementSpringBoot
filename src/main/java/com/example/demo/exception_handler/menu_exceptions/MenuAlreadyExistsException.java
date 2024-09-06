package com.example.demo.exception_handler.menu_exceptions;

public class MenuAlreadyExistsException extends  RuntimeException{
    public  MenuAlreadyExistsException(String message){
        super(message);
    }
}
