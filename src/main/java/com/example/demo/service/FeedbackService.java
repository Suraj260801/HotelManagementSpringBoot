package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.request_dto.UpdateFeedbackRequestDTO;
import com.example.demo.exception_handler.feedback_exceptions.FeedbackNotFoundException;
import com.example.demo.exception_handler.feedback_exceptions.InvalidFeedbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Feedback;
import com.example.demo.repository.FeedbackRepository;

@Service
public class FeedbackService {


    // Repository to interact with Feedback entities in the database
    @Autowired
    private FeedbackRepository feedbackRepository;

    /**
     * Retrieves all feedback from the repository.
     * Throws an exception if no feedbacks are found.
     *
     * @return A list of all feedbacks.
     * @throws FeedbackNotFoundException If no feedbacks are found.
     */
    public List<Feedback> findAllFeedbacks() {
        List<Feedback> feedbacks=feedbackRepository.findAll();
        if(feedbacks.isEmpty()){
            throw new FeedbackNotFoundException("No Feedbacks Found");
        }
        return feedbacks;
    }

    /**
     * Saves a new or updated feedback to the repository.
     *
     * @param feedback The feedback entity to be saved.
     * @return The saved feedback entity.
     */
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }



    public Feedback updateFeedback(UpdateFeedbackRequestDTO feedbackRequestDTO){
        validateRequestDTO(feedbackRequestDTO);
        Optional<Feedback> existingFeedback=feedbackRepository.getFeedbackByEmail(feedbackRequestDTO.getEmail());
        if(existingFeedback.isEmpty()){
            throw new FeedbackNotFoundException("No feedback found with email provided");
        }
        existingFeedback.get().setFeedback(feedbackRequestDTO.getFeedback());
        return saveFeedback(existingFeedback.get());
    }

    /**
     * Retrieves a feedback by its Email.
     * Throws an exception if the Email is invalid or if the feedback is not found.
     *
     * @param email The Email of the feedback to retrieve.
     * @return The found feedback entity.
     * @throws FeedbackNotFoundException If the feedback Email is invalid or if no feedback is found with the given Email.
     */
    public Feedback findFeedbackByEmail(String email) {
        if(email.trim().isEmpty()){
            throw new FeedbackNotFoundException("Invalid Customer email provided, Please Enter Valid Customer email");
        }
        Optional<Feedback> feedback=feedbackRepository.getFeedbackByEmail(email);
        return feedback.orElseThrow(()->new FeedbackNotFoundException("No Feedback found with Id "+email));
    }

    /**
     * Deletes a feedback by its ID from the repository.
     *
     * @param email The ID of the feedback to delete.
     */
    public void deleteFeedback(String email) {
        if(email==null||email.trim().isEmpty()){
            throw new FeedbackNotFoundException("Invalid Email Provided");
        }
        Optional<Feedback> feedback=feedbackRepository.getFeedbackByEmail(email);
        if(feedback.isEmpty()){
            throw new FeedbackNotFoundException("No Feedback Found for email "+email);
        }
        feedbackRepository.deleteById(feedback.get().getId());
    }



    public void validateRequestDTO(UpdateFeedbackRequestDTO feedbackRequestDTO){
        if(feedbackRequestDTO==null){
            throw new InvalidFeedbackException("feedback and email can't be null");
        }else if(feedbackRequestDTO.getFeedback()==null&&feedbackRequestDTO.getEmail()==null){
            throw new InvalidFeedbackException("feedback and email can't be null");
        }else if(feedbackRequestDTO.getFeedback()==null||feedbackRequestDTO.getFeedback().trim().isEmpty()){
            throw new InvalidFeedbackException("feedback can't be null or empty");
        }else if(feedbackRequestDTO.getEmail()==null||feedbackRequestDTO.getEmail().trim().isEmpty()){
            throw new InvalidFeedbackException("email can't be null");
        }
    }
}
