package com.example.demo.exception_handler.bill_exceptions;

public class NoBillFoundException extends  RuntimeException{
    public NoBillFoundException(String message){
        super(message);
    }
}
