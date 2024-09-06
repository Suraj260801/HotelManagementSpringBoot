package com.example.demo.exception_handler.feedback_exceptions;

public class FeedbackNotFoundException extends RuntimeException{
    public FeedbackNotFoundException(String message){
        super(message);
    }
}
