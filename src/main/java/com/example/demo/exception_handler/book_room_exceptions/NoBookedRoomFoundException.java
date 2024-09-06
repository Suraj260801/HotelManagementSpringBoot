package com.example.demo.exception_handler.book_room_exceptions;

public class NoBookedRoomFoundException extends  RuntimeException{
    public NoBookedRoomFoundException(String message){
        super(message);
    }
}
