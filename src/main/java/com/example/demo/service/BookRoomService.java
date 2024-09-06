package com.example.demo.service;


import com.example.demo.dto.request_dto.BookRoomRequestDTO;
import com.example.demo.dto.response_dto.RoomBookingSuccessDTO;
import com.example.demo.dto.response_dto.RoomDTO;
import com.example.demo.exception_handler.book_room_exceptions.NoBookedRoomFoundException;
import com.example.demo.exception_handler.book_room_exceptions.RoomNotAvailableException;
import com.example.demo.exception_handler.room_exceptions.InvalidRoomException;
import com.example.demo.models.Bill;
import com.example.demo.models.BookRoom;
import com.example.demo.models.Room;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.BookRoomRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookRoomService {

    // Repository to perform CRUD operations on BookRoom entities
    @Autowired
    private BookRoomRepository bookRoomRepository;

    // Repository to interact with Room entities
    @Autowired
    private RoomRepository roomRepository;

    // Repository to perform CRUD operations on Bill entities
    @Autowired
    private BillRepository billRepository;

    // Service to update Room availability when it's booked by customer
    @Autowired
    private RoomService roomService;

    /**
     * Retrieves a booked room by its ID. Throws an exception if the room is not found.
     *
     * @param id The ID of the booked room to retrieve.
     * @return The booked room if found.
     */
    public BookRoom getBookedRoomById(Long id){
        validateGetBookedRoomById(id);
        Optional<BookRoom> bookedRoom=bookRoomRepository.findById(id);
        return bookedRoom.orElseThrow( ()->new NoBookedRoomFoundException("No Booked Room Found with Id "+id));
    }

    /**
     * Retrieves a booked room by the customer's name. Throws an exception if no room is found.
     *
     * @param name The name of the customer who booked the room.
     * @return The booked room if found.
     */
    public BookRoom getBookedRoomByCustomerName(String name){
        Optional<BookRoom> bookedRoom=bookRoomRepository.getBookRoomByCustomerName(name);
        return bookedRoom.orElseThrow(()->new NoBookedRoomFoundException("No room found booked by "+name));
    }

    /**
     * Retrieves a list of all booked rooms. Throws an exception if no booked rooms are found.
     *
     * @return A list of all booked rooms.
     */
    public List<BookRoom> getAllBookedRooms(){
        List<BookRoom> bookedRooms=bookRoomRepository.findAll();
        if(bookedRooms.isEmpty()){
            throw new NoBookedRoomFoundException("No Booked Rooms Found");
        }
        return bookRoomRepository.findAll();
    }

    /**
     * Books a room for a customer. Sets the room to unavailable and generates a bill for the booking.
     *
     * @param roomRequestDTO The BookRoom object containing the booking details.
     * @return The newly booked room.
     */
    public RoomBookingSuccessDTO bookRoom(BookRoomRequestDTO roomRequestDTO){
        validateBookRoom(roomRequestDTO);
        BookRoom newBookedRoom=validateRoomsAvailability(roomRequestDTO);
        bookRoomRepository.save(newBookedRoom);
        RoomBookingSuccessDTO successDTO=new RoomBookingSuccessDTO();
        successDTO.setId(newBookedRoom.getId());
        successDTO.setCustomerName(newBookedRoom.getCustomerName());
        successDTO.setBookedRooms(roomRequestDTO.getRooms());
        successDTO.setBookingDate(LocalDateTime.now());
        return successDTO;
    }

    /**
     * Validates the booked room ID. Throws an exception if the ID is invalid (less than 0).
     *
     * @param id The ID of the booked room to validate.
     */
    public void validateGetBookedRoomById(Long id){
        if(id<0){
            throw new NoBookedRoomFoundException("No Booked Room Found with Id "+id+"\nInvalid Booked Room Id ,Please Enter Valid Id");
        }
    }

    /**
     * Validates the room availability for booking. Throws an exception if the room is unavailable or does not exist.
     *
     * @param roomRequestDTO The BookRoom object containing the booking details.
     * @return The available room to be booked.
     */
    public void validateBookRoom(BookRoomRequestDTO roomRequestDTO) {
        if (roomRequestDTO == null) {
            throw new InvalidRoomException("Invalid Room Data, Please Enter valid customer name and room type and room counts");
        } else if (roomRequestDTO.getCustomerName() == null && roomRequestDTO.getRooms() == null) {
            throw new InvalidRoomException("Room type and customer can't be null");
        } else if (roomRequestDTO.getCustomerName() == null || roomRequestDTO.getCustomerName().trim().isEmpty()) {
            throw new InvalidRoomException("Invalid Customer Name ,Please provide valid customer name");
        } else if (roomRequestDTO.getRooms() == null || roomRequestDTO.getRooms().isEmpty()) {
            throw new InvalidRoomException("Invalid Room type, Please provide valid room type");
        }
    }

    /**
     * Validates the availability of the rooms present in db with requested rooms and room count by the customer
     * Throws an exception if requested room either not available or does not exists.
     * @param roomRequestDTO contains customer name and info about room type along with room count
     *
     * @return BookedRoom Object which has list of booked rooms with their count and bill object embedded in it
     * */
    public BookRoom validateRoomsAvailability(BookRoomRequestDTO roomRequestDTO){

        //create the hashmaps of requested rooms ad available rooms to send as a response in
        //the case of un-availability of rooms or if requested room does not exists.
        HashMap<String,Integer> availableRoomsMap=getAvailableRoomsMap(roomRequestDTO);
        HashMap<String,Integer> requestedRoomsMap=getAllRequestedRoomsMap(roomRequestDTO);

        List<RoomDTO> unavailableRooms=new ArrayList<>();
        for(RoomDTO dto:roomRequestDTO.getRooms()){
            Optional<Room> r=roomRepository.getRoomByType(dto.getRoomType());
            if(r.isEmpty()){
                unavailableRooms.add(dto);
            }else{
                if(r.get().getAvailableCount()<dto.getRoomCount()){
                    unavailableRooms.add(dto);
                }
            }
        }
        if(!unavailableRooms.isEmpty()){
            throw new RoomNotAvailableException("Requested Number Of Rooms Are Not Available",requestedRoomsMap,availableRoomsMap);
        }


        //process the available rooms
        //update availability of existing rooms after allocation
        //generate total bill.
        Set<Room> rawBookedRooms=new HashSet<>();
        double totalAmount=0.0;
        for(RoomDTO roomDTO:roomRequestDTO.getRooms()){
            Optional<Room> room=roomRepository.getRoomByType(roomDTO.getRoomType());
            Integer availableRoomCount=room.get().getAvailableCount();
            Integer requestedRoomCount=roomDTO.getRoomCount();
            int newUpdatedRoomCount=availableRoomCount-requestedRoomCount;
            room.get().setAvailableCount(newUpdatedRoomCount);
            Boolean isRoomAvailable=newUpdatedRoomCount>0;
            room.get().setAvailable(isRoomAvailable);
            roomService.updateRoom(room.get().getId(),room.get());

            totalAmount=totalAmount+room.get().getPrice()*roomDTO.getRoomCount();
            rawBookedRooms.add(room.get());
        }
        Bill bill=new Bill(totalAmount);
        BookRoom bookedRooms=new BookRoom(roomRequestDTO.getCustomerName(),rawBookedRooms,bill);
        Set<BookRoom> bookRoomHashSet=new HashSet<>();
        bookRoomHashSet.add(bookedRooms);
        for(Room r:rawBookedRooms){
            r.setBookRooms(bookRoomHashSet);
        }
        return bookedRooms;

    }


   /**
    * Fetches all rooms from the db and creates the list of the available rooms
    * @param roomRequestDTO contains the customer name and rooms requested ,each room has type and count info
    *
    * @return List of all available rooms in db
    * */
    public List<Room> getListOfAllAvailableRooms(BookRoomRequestDTO roomRequestDTO){
        List<Room> availableRooms=roomService.findAllRooms();
        HashMap<String,Integer> requestedRooms=getAllRequestedRoomsMap(roomRequestDTO);
        if(availableRooms.isEmpty()){
            throw new RoomNotAvailableException("Currently No Rooms are available",requestedRooms,new HashMap<>());
        }
        return availableRooms;
    }

    /**
     * Converts all available rooms in the HashMap of room type and count to send as a response
     * @param roomRequestDTO contains the customer name and rooms requested ,each room has type and count info
     *
     * @return HashMap of all available rooms in database with room_type as key and room_count as value
     * */
    public HashMap<String,Integer> getAvailableRoomsMap(BookRoomRequestDTO roomRequestDTO){
        List<Room> allRooms=getListOfAllAvailableRooms(roomRequestDTO);
        HashMap<String,Integer> availableRooms=new HashMap<>();
        for(Room r:allRooms){
            availableRooms.put(r.getType(),r.getAvailableCount());
        }
        return availableRooms;
    }

    /**
     * Creates the HashMap of requested room type and no_of_rooms from request object of requestDTO
     * @param roomRequestDTO contains the customer name and rooms requested ,each room has type and count info
     *
     * @return the HashMap of room type and count of the room
     * */
    public HashMap<String,Integer> getAllRequestedRoomsMap(BookRoomRequestDTO roomRequestDTO){
        HashMap<String,Integer> requestedRooms=new HashMap<>();
        for(RoomDTO roomDTO:roomRequestDTO.getRooms()){
            requestedRooms.put(roomDTO.getRoomType(),roomDTO.getRoomCount());
        }
        return requestedRooms;
    }
}
