package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import com.example.demo.exception_handler.bill_exceptions.NoBillFoundException;
import com.example.demo.models.BookRoom;
import com.example.demo.models.FoodOrder;
import com.example.demo.repository.BookRoomRepository;
import com.example.demo.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Bill;
import com.example.demo.repository.BillRepository;
@Service
public class BillService {

    // Repository to perform CRUD operations on Bill entities
    @Autowired
    private BillRepository billRepository;

    // Repository to perform CRUD operations on FoodOrder entities
    @Autowired
    private FoodOrderRepository foodOrderRepository;

    // Repository to perform CRUD operations on BookRoom entities
    @Autowired
    private BookRoomRepository bookRoomRepository;


    /**
     * Retrieve all bills from the database.
     *
     * @return a list of all bills
     */
    public List<Bill> findAllBills() {
        return billRepository.findAll();
    }


    /**
     * Save a new or updated bill to the database.
     *
     * @param bill the bill to be saved
     * @return the saved bill
     */
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    /**
     * Find a specific bill by its ID.
     *
     * @param id the ID of the bill
     * @return an Optional containing the found bill, or empty if not found
     */
    public Optional<Bill> findBillById(Long id) {

        return billRepository.findById(id);
    }


    /**
     * Delete a bill by its ID.
     *
     * @param id the ID of the bill to be deleted
     */
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }



    /**
     * Retrieve the bill associated with a specific food order by its ID.
     *
     * @param orderId the ID of the food order
     * @return the bill associated with the food order
     * @throws NoBillFoundException if no bill is found for the given food order ID
     */
    public Bill getFoodOrderBillById(Long orderId){
        Optional<FoodOrder> foodOrder=foodOrderRepository.findById(orderId);
        if(foodOrder.isEmpty()){
            throw new NoBillFoundException("No Bill Found for food Order with Id "+orderId);
        }
        return foodOrder.get().getBill();
    }

    /**
     * Retrieve the bill associated with a specific booked room by its ID.
     *
     * @param bookedRoomId the ID of the booked room
     * @return the bill associated with the booked room
     * @throws NoBillFoundException if no bill is found for the given booked room ID
     */
    public Bill getBookedRoomBillById(Long bookedRoomId){
        Optional<BookRoom> bookedRoom=bookRoomRepository.findById(bookedRoomId);
        if(bookedRoom.isEmpty()){
            throw new NoBillFoundException("No Bill Found for the Booked Room with Id "+bookedRoomId);
        }
        return bookedRoom.get().getBill();
    }

    /**
     * Retrieve the bill associated with a food order by the customer's name.
     *
     * @param name the name of the customer
     * @return the bill associated with the customer's food order
     * @throws NoBillFoundException if no bill is found for the given customer name
     */
    public Bill getFoodOrderBillByCustomerName(String name){
        Optional<FoodOrder> foodOrder=foodOrderRepository.getFoodOrderByCustomerName(name);
        if(foodOrder.isEmpty()){
            throw new NoBillFoundException("No Bill Found for the Food Order with Customer name "+name);
        }
        return foodOrder.get().getBill();
    }

    /**
     * Retrieve the bill associated with a booked room by the customer's name.
     *
     * @param name the name of the customer
     * @return the bill associated with the customer's booked room
     * @throws NoBillFoundException if no bill is found for the given customer name
     */
    public Bill getBookedRoomBillByCustomerName(String name){
        Optional<BookRoom> bookedRoom=bookRoomRepository.getBookRoomByCustomerName(name);
        if(bookedRoom.isEmpty()){
            throw new NoBillFoundException("No Bill Found for the Booked Room with Customer Name "+name);
        }
        return bookedRoom.get().getBill();
    }
}
