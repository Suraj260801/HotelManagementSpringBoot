package com.example.demo.dto.request_dto;

import com.example.demo.dto.response_dto.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRoomRequestDTO {
    private String customerName;
    private List<RoomDTO> rooms;
}
