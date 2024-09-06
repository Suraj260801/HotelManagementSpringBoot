package com.example.demo.dto.response_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodNotAvailableExceptionDTO {
    private String message;
    private HashMap<String,Integer> orderedFoods;
    private HashMap<String,Integer> availableFoods;
}
