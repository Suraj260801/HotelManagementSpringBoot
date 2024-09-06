package com.example.demo.exception_handler.room_exceptions;

public class RoomNotFoundException extends  RuntimeException{

    public RoomNotFoundException(String message){
           super(message);
    }
}
