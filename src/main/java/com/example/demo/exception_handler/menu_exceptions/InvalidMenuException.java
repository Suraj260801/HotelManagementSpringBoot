package com.example.demo.exception_handler.menu_exceptions;

public class InvalidMenuException extends  RuntimeException{
    public InvalidMenuException(String message){
        super(message);
    }
}
