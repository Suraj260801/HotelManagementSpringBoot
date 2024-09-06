package com.example.demo.exception_handler.feedback_exceptions;


public class InvalidFeedbackException extends  RuntimeException{
    public InvalidFeedbackException(String message){
        super(message);
    }
}
