package com.example.demo.exception_handler.room_exceptions;

public class InvalidRoomException extends  RuntimeException{
    public InvalidRoomException(String message){
        super(message);
    }
}
