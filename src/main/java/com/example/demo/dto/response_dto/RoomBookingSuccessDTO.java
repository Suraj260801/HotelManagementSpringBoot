package com.example.demo.dto.response_dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingSuccessDTO {
    private Long id;
    private String customerName;
    private LocalDateTime bookingDate;
    private List<RoomDTO> bookedRooms;
}
