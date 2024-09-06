package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.exception_handler.room_exceptions.InvalidRoomException;
import com.example.demo.exception_handler.room_exceptions.RoomAlreadyExistsException;
import com.example.demo.exception_handler.room_exceptions.RoomNotFoundException;
import com.example.demo.models.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Room;
import com.example.demo.repository.RoomRepository;

@Service
public class RoomService {

    // Repository to handle CRUD operations for Room entities.
    @Autowired
    private RoomRepository roomRepository;


    /**
     * Retrieves all rooms from the repository.
     *
     * @return A list of all rooms.
     */
    public List<Room> findAllRooms(){
        List<Room> allRooms=roomRepository.findAll();
        if(allRooms.isEmpty()){
            throw new RoomNotFoundException("No Rooms Found");
        }
        return allRooms;
    }

    /**
     * Saves a new room in the repository.
     * Validates the room data and throws an exception if it is invalid or if a room with the same type already exists.
     *
     * @param room The room to save.
     * @return The saved room entity.
     * @throws RoomAlreadyExistsException If a room with the same type already exists.
     * @throws InvalidRoomException If the room data is invalid.
     */
    public Room saveRoom(Room room){
        validateSaveRoom(room);
        return roomRepository.save(room);
    }

    /**
     * Finds a room by its ID.
     * Throws an exception if the ID is invalid or the room is not found.
     *
     * @param id The ID of the room to find.
     * @return The room associated with the given ID.
     * @throws RoomNotFoundException If the room is not found or the ID is invalid.
     */
    public Room findRoomById(Long id){
        validateFindRoomById(id);
        return roomRepository.findById(id).orElseThrow(()->new RoomNotFoundException("Room Not Found with Id "+id));
    }


    /**
     * Deletes a room by its ID.
     * Throws an exception if the room is not found.
     *
     * @param id The ID of the room to delete.
     * @throws RoomNotFoundException If the room is not found.
     */
    public void deleteRoom(Long id){
        Optional<Room> room= roomRepository.findById(id);
        validateDeleteRoom(room,id);
        roomRepository.deleteById(id);
    }

    /**
     * Retrieves a room by its type.
     * Throws an exception if no room with the given type is found.
     *
     * @param type The type of the room to find.
     * @return The room with the given type.
     * @throws RoomNotFoundException If no room with the given type is found.
     */
    public Room getRoomByType(String type){
        Optional<Room> room=roomRepository.getRoomByType(type);
        validateGetRoomByType(room,type);
        return room.get();
    }

    /**
     * Validates the room data before saving a new room.
     * Ensures that the room type, availability, and price are not null, and that the price is within a valid range.
     * Throws an exception if the room data is invalid or if a room with the same type already exists.
     *
     * @param room The room to validate.
     * @throws RoomAlreadyExistsException If a room with the same type already exists.
     * @throws InvalidRoomException If the room data is invalid.
     */
    private void validateSaveRoom(Room room) {
       if(room.getType()==null&&room.getAvailable()==null&&room.getPrice()==null){
            throw new InvalidRoomException("Room type,availability and price can't be null");
        } else if(room.getType()==null||room.getType().trim().isEmpty()){
            throw new InvalidRoomException("Room type can't be null or empty");
        }else if(room.getAvailable()==null){
            throw  new InvalidRoomException("Room availability can't be null or empty");
        }else if(room.getPrice()==null||room.getPrice()<0||room.getPrice()>1000000){
            throw  new InvalidRoomException("Room price can't be null, and must be in range 0-10L");
        }

        Optional<Room> existingRoom=roomRepository.getRoomByType(room.getType());
        if(existingRoom.isPresent()){
            throw new RoomAlreadyExistsException("Room Already Exists With Type "+ existingRoom.get().getType());
        }
    }

    /**
     * Validates the room ID before finding a room.
     * Throws an exception if the ID is invalid.
     *
     * @param id The ID to validate.
     * @throws RoomNotFoundException If the ID is invalid.
     */
    public void validateFindRoomById(Long id){
        if(id<0){
            throw new RoomNotFoundException("Invalid Room Id,Enter valid Room Id");
        }
    }


    /** Updates the room based on oldId and new Room data
     *
     * @param id the old id for which room data will be overridden
     * @param modifiedRoom new room data to replace old date corresponding to id
     * */
    public Room updateRoom(Long id,Room modifiedRoom){
          Room existingRoom=findRoomById(id);
          modifiedRoom.setId(id);
          return roomRepository.save(modifiedRoom);
    }

    /**
     * Validates the room before deleting it.
     * Throws an exception if the room is not found.
     *
     * @param room The room to validate.
     * @param id The ID of the room to validate.
     * @throws RoomNotFoundException If the room is not found.
     */
    public void validateDeleteRoom(Optional<Room> room,Long id){
        if(room.isEmpty()){
            throw new RoomNotFoundException("Room Not Found with Id "+id);
        }
    }

    /**
     * Validates the room type before retrieving a room by type.
     * Throws an exception if no room with the given type is found.
     *
     * @param rooms The room to validate.
     * @param type The type of the room to validate.
     * @throws RoomNotFoundException If no room with the given type is found.
     */
    public void validateGetRoomByType(Optional<Room> rooms,String type){
        if(rooms.isEmpty()){
            throw new RoomNotFoundException("Rooms Not Found with Type "+type);
        }
    }
}
