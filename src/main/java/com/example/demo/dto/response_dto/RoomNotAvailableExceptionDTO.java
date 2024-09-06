package com.example.demo.dto.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomNotAvailableExceptionDTO {
    private String message;
    private HashMap<String,Integer> requestedRooms;
    private HashMap<String,Integer> availableRooms;
}
