package com.example.demo.exception_handler;

import com.example.demo.dto.response_dto.ExceptionDTO;
import com.example.demo.dto.response_dto.FoodNotAvailableExceptionDTO;
import com.example.demo.dto.response_dto.RoomNotAvailableExceptionDTO;
import com.example.demo.exception_handler.bill_exceptions.NoBillFoundException;
import com.example.demo.exception_handler.book_room_exceptions.NoBookedRoomFoundException;
import com.example.demo.exception_handler.book_room_exceptions.RoomNotAvailableException;
import com.example.demo.exception_handler.feedback_exceptions.FeedbackNotFoundException;
import com.example.demo.exception_handler.feedback_exceptions.InvalidFeedbackException;
import com.example.demo.exception_handler.food_exceptions.FoodItemNotAvailableException;
import com.example.demo.exception_handler.food_exceptions.FoodNotFoundException;
import com.example.demo.exception_handler.food_order_exceptions.FoodOrderNotFoundException;
import com.example.demo.exception_handler.food_order_exceptions.InvalidFoodOrderException;
import com.example.demo.exception_handler.menu_exceptions.InvalidMenuException;
import com.example.demo.exception_handler.menu_exceptions.MenuAlreadyExistsException;
import com.example.demo.exception_handler.menu_exceptions.MenuNotFoundException;
import com.example.demo.exception_handler.room_exceptions.InvalidRoomException;
import com.example.demo.exception_handler.room_exceptions.RoomAlreadyExistsException;
import com.example.demo.exception_handler.room_exceptions.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling various exceptions thrown across the application.
 * Provides a centralized way to manage exceptions and return appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    //Room related exceptions

    /**
     * Handles exceptions when a room is not found.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 404 NOT FOUND status and the exception message.
     */
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Object> handleRoomNotFoundException(RoomNotFoundException e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(e.getMessage()));
    }

    /**
     * Handles exceptions when a room already exists.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 500 INTERNAL SERVER ERROR status and the exception message.
     */
    @ExceptionHandler(RoomAlreadyExistsException.class)
    public ResponseEntity<Object> handleRoomAlreadyExistsException(RoomAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDTO(e.getMessage()));
    }

    /**
     * Handles exceptions related to invalid room details.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 400 BAD REQUEST status and the exception message.
     */
    @ExceptionHandler(InvalidRoomException.class)
    public ResponseEntity<Object> handleNoSuchCustomerExistsException(InvalidRoomException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO(e.getMessage()));
    }

    /**
     * Handles exceptions when the HTTP request body is missing or invalid.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 400 BAD REQUEST status and a generic error message.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO(e.getMessage()));
    }

    /**
     * Handles exceptions when a room is not available.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 404 NOT FOUND status and the exception message.
     */
    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<Object> handleRoomNotAvailableException(RoomNotAvailableException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RoomNotAvailableExceptionDTO(e.getMessage(),e.getRequestedRooms(),e.getAvailableRooms()));
    }

    /**
     * Handles exceptions when no booked room is found.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 404 NOT FOUND status and the exception message.
     */
    @ExceptionHandler(NoBookedRoomFoundException.class)
    public ResponseEntity<Object> handleNoBookedRoomFoundException(NoBookedRoomFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(e.getMessage()));
    }



    //Bill Related Exception

    // Bill Related Exception
    /**
     * Handles exceptions when a bill is not found.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 404 NOT FOUND status and the exception message.
     */
    @ExceptionHandler(NoBillFoundException.class)
    public ResponseEntity<Object> handleNoBillFoundException(NoBillFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(e.getMessage()));
    }




    //Food Order Related Exceptions
    /**
     * Handles exceptions when a food order is not found.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 404 NOT FOUND status and the exception message.
     */
    @ExceptionHandler(FoodOrderNotFoundException.class)
    public ResponseEntity<Object> handleFoodOrderNotFoundException(FoodOrderNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(e.getMessage()));
    }

    /**
     * Handles exceptions when a food order is invalid.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 400 BAD REQUEST status and the exception message.
     */
    @ExceptionHandler(InvalidFoodOrderException.class)
    public ResponseEntity<Object> handleInvalidFoodOrderException(InvalidFoodOrderException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO(e.getMessage()));
    }


    //Food Related Exceptions
    /**
     * Handles exceptions when a food item is not found.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 404 NOT FOUND status and the exception message.
     */
    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseEntity<Object> handleFoodNotFoundException(FoodNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(e.getMessage()));
    }

    /**
     * Handles exceptions when a food item is not available.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 404 NOT FOUND status and the exception message.
     */
    @ExceptionHandler(FoodItemNotAvailableException.class)
    public ResponseEntity<Object> handleFoodItemNotAvailableException(FoodItemNotAvailableException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FoodNotAvailableExceptionDTO(e.getMessage(), e.getOrderedFoodItems(),e.getAvailableFoodItems()));
    }



    //Menu Related Exceptions
    /**
     * Handles exceptions when a menu item is not found.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 404 NOT FOUND status and the exception message.
     */
    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<Object> handleMenuNotFoundException(MenuNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(e.getMessage()));
    }

    /**
     * Handles exceptions related to invalid menu details.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 400 BAD REQUEST status and the exception message.
     */
    @ExceptionHandler(InvalidMenuException.class)
    public ResponseEntity<Object> handleInvalidMenuException(InvalidMenuException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO(e.getMessage()));
    }


    /**
     * Handles exceptions when a menu item already exists.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 409 CONFLICT status and the exception message.
     */
    @ExceptionHandler(MenuAlreadyExistsException.class)
    public ResponseEntity<Object> handleMenuAlreadyExistsException(MenuAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionDTO(e.getMessage()));
    }


    //Feedback Related Exceptions
    /**
     * Handles exceptions when feedback is not found.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity with a 404 NOT FOUND status and the exception message.
     */
    @ExceptionHandler(FeedbackNotFoundException.class)
    public ResponseEntity<Object> handleFeedbackNotFoundException(FeedbackNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(e.getMessage()));
    }



    @ExceptionHandler(InvalidFeedbackException.class)
    public ResponseEntity<Object> handleInvalidFeedbackException(InvalidFeedbackException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}


