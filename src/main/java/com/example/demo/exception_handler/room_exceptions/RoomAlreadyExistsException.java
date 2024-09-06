package com.example.demo.exception_handler.room_exceptions;

public class RoomAlreadyExistsException extends  RuntimeException{
    public RoomAlreadyExistsException(String  message){
        super(message);
    }
}
