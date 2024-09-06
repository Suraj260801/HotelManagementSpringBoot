package com.example.demo.controller;

import com.example.demo.dto.request_dto.BookRoomRequestDTO;
import com.example.demo.dto.response_dto.RoomBookingSuccessDTO;
import com.example.demo.models.BookRoom;
import com.example.demo.service.BookRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/book_room")
public class BookRoomController {


    // Injects the BookRoomService to handle business logic related to booking rooms
    @Autowired
    private BookRoomService bookRoomService;



    /**
     * Retrieves all booked rooms.
     * @return ResponseEntity containing a list of all BookRoom objects.
     */
    @GetMapping
    public ResponseEntity<List<BookRoom>> getAllBookedRooms(){
        List<BookRoom> bookedRooms=bookRoomService.getAllBookedRooms();
        return ResponseEntity.ok(bookedRooms);
    }


    /**
     * Retrieves a booked room by its ID.
     * @param id The ID of the booked room.
     * @return ResponseEntity containing the BookRoom object related to the specified ID.
     */
    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<Object> getBookedRoomById(@PathVariable  Long id){
        BookRoom bookedRoom=bookRoomService.getBookedRoomById(id);
        return ResponseEntity.ok(bookedRoom);
    }


    /**
     * Retrieves a booked room by the customer's name.
     * @param name The name of the customer.
     * @return ResponseEntity containing the BookRoom object related to the specified customer name.
     */
    @GetMapping("/get_by_name/{name}")
    public ResponseEntity<Object> getBookedRoomByName(@PathVariable  String name){
        BookRoom bookedRoom=bookRoomService.getBookedRoomByCustomerName(name);
        return ResponseEntity.ok(bookedRoom);

    }

    /**
     * Books a new room.
     * @param roomRequestDTO The BookRoom object containing the booking details.
     * @return ResponseEntity containing the booked room details.
     */
    @PostMapping
    public ResponseEntity<Object> bookRoom(@RequestBody BookRoomRequestDTO roomRequestDTO){
        RoomBookingSuccessDTO successDTO=bookRoomService.bookRoom(roomRequestDTO);
        return ResponseEntity.ok(successDTO);
    }
}
