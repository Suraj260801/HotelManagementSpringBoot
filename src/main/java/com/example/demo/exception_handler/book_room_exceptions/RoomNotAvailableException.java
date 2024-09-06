package com.example.demo.exception_handler.book_room_exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class RoomNotAvailableException extends  RuntimeException{
    private HashMap<String,Integer> requestedRooms;
    private HashMap<String,Integer> availableRooms;
    public RoomNotAvailableException(String message,HashMap<String,Integer> requestedRooms,HashMap<String,Integer> availableRooms){
      super(message);
      this.availableRooms=availableRooms;
      this.requestedRooms=requestedRooms;
    }
}
