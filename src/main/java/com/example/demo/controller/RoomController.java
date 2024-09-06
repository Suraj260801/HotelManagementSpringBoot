package com.example.demo.controller;
import com.example.demo.models.Room;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.RoomService;


@RestController
@RequestMapping("/rooms")
public class RoomController {

    // Injects the RoomService to handle business logic related to rooms
    @Autowired
    private RoomService roomService;


    /**
     * Retrieves a list of all rooms.
     * @return ResponseEntity containing the list of all Room objects.
     */
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms=roomService.findAllRooms();
        return ResponseEntity.ok(rooms);
    }

    /**
     * Retrieves a specific room by its ID.
     * @param id The ID of the room to retrieve.
     * @return ResponseEntity containing the Room object if found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoomById(@PathVariable Long id) {
        Room room=roomService.findRoomById(id);
        return ResponseEntity.ok(room);
    }


    /**
     * Retrieves a room by its type.
     * @param type The type of the room to retrieve.
     * @return ResponseEntity containing the Room object if found.
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<Object> getRoomByType(@PathVariable String type){
        Room room=roomService.getRoomByType(type);
        return ResponseEntity.ok(room);
    }

    /**
     * Creates a new room.
     * @param room The Room object representing the new room to be created.
     * @return ResponseEntity containing the created Room object with HTTP status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Object> createRoom(@RequestBody Room room) {
            Room savedRoom=roomService.saveRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    /**
     * Updates an existing room by its ID.
     * @param id The ID of the room to update.
     * @param room The updated Room object.
     * @return ResponseEntity containing the updated Room object with HTTP status 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
            Room savedRoom=roomService.updateRoom(id,room);
            return ResponseEntity.status(HttpStatus.OK).body(savedRoom);
    }

    /**
     * Deletes a room by its ID.
     * @param id The ID of the room to delete.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
            roomService.deleteRoom(id);
            return ResponseEntity.ok("Room Deleted Successfully with Id "+id);
    }


}
