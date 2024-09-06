package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Bill;
import com.example.demo.service.BillService;

@RestController
@RequestMapping("/bills")
public class BillController {

    // Injects the BillService to handle business logic related to Bill entities
    @Autowired
    private BillService billService;


    /**
     * Retrieves all bills.
     * @return List of all Bill objects.
     */
    @GetMapping
    public List<Bill> getAllBills() {
        return billService.findAllBills(); 
    }


    /**
     * Creates a new bill.
     * @param bill The Bill object to be created.
     * @return The created Bill object.
     */
    @PostMapping
    public Bill createBill(@RequestBody Bill bill) {
       return billService.saveBill(bill);
    }


    /**
     * Retrieves a bill by its ID.
     * @param id The ID of the bill.
     * @return The Bill object if found, otherwise null.
     */
    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id) {
        return billService.findBillById(id).orElse(null);
    }


    /**
     * Updates an existing bill with a given ID.
     * @param id The ID of the bill to be updated.
     * @param bill The updated Bill object.
     * @return The updated Bill object.
     */
    @PostMapping("/{id}")
    public Bill updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        bill.setId(id);
        return billService.saveBill(bill);
   
    }


    /**
     * Deletes a bill by its ID.
     * @param id The ID of the bill to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
         billService.deleteBill(id);
    
    }


    /**
     * Retrieves a bill for a specific food order by order ID.
     * @param orderId The ID of the food order.
     * @return The Bill object related to the specified food order.
     */
    @GetMapping("/food_order/{orderId}")
    public ResponseEntity<Object> getFoodOrderBillById(@PathVariable Long orderId){
        Bill bill=billService.getFoodOrderBillById(orderId);
        return ResponseEntity.ok(bill);
    }


    /**
     * Retrieves a bill for a specific booked room by room ID.
     * @param bookedRoomId The ID of the booked room.
     * @return The Bill object related to the specified booked room.
     */
    @GetMapping("/book_room/{bookedRoomId}")
    public ResponseEntity<Object> getBookedRoomBillById(@PathVariable Long bookedRoomId){
        Bill bill=billService.getBookedRoomBillById(bookedRoomId);
        return ResponseEntity.ok(bill);
    }


    /**
     * Retrieves a food order bill by the customer's name.
     * @param name The name of the customer.
     * @return The Bill object related to the customer's food order.
     */
    @GetMapping("/food_order/customer_name/{name}")
    public ResponseEntity<Object> getFoodOrderBillByCustomerName(@PathVariable String name){
        Bill bill=billService.getFoodOrderBillByCustomerName(name);
        return ResponseEntity.ok(bill);
    }

    /**
     * Retrieves a booked room bill by the customer's name.
     * @param name The name of the customer.
     * @return The Bill object related to the customer's booked room.
     */
    @GetMapping("/book_room/customer_name/{name}")
    public ResponseEntity<Object> getBookedRoomBillByCustomerName(@PathVariable String name){
         Bill bill=billService.getBookedRoomBillByCustomerName(name);
         return ResponseEntity.ok(bill);
    }

}
