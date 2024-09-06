package com.example.demo.controller;

import java.util.List;

import com.example.demo.dto.request_dto.UpdateFeedbackRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Feedback;
import com.example.demo.service.FeedbackService;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    // Injects the FeedbackService to handle business logic related to feedback
    @Autowired
    private FeedbackService feedbackService;


    /**
     * Retrieves all feedbacks.
     * @return List of all Feedback objects.
     */
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
          List<Feedback> feedbacks=feedbackService.findAllFeedbacks();
          return ResponseEntity.ok(feedbacks);
    }


    /**
     * Creates a new feedback entry.
     * @param feedback The Feedback object to be created.
     * @return The created Feedback object.
     */
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
         Feedback savedFeedback=feedbackService.saveFeedback(feedback);
         return ResponseEntity.ok(savedFeedback);
    }


    /**
     * Retrieves a feedback entry by its Customer Email.
     * @param email The Customer Email of the feedback to retrieve.
     * @return The Feedback object if found.
     */
    @GetMapping("/{email}")
    public ResponseEntity<Feedback> getFeedbackByCustomerEmail(@PathVariable String email) {
        Feedback feedback=feedbackService.findFeedbackByEmail(email);
        return ResponseEntity.ok(feedback);
    }


    /**
     * Updates an existing feedback entry with a given ID.
     * @param feedbackRequestDTO The updated Feedback object.
     * @return The updated Feedback object.
     */
    @PutMapping
    public ResponseEntity<String> updateFeedback( @RequestBody UpdateFeedbackRequestDTO feedbackRequestDTO) {
        feedbackService.updateFeedback(feedbackRequestDTO);
        return ResponseEntity.ok("Feedback Updated Successfully");
    }


    /**
     * Deletes a feedback entry by its Email.
     * @param email The Email of the feedback to delete.
     */
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteFeedbackByEmail(@PathVariable String email) {
         feedbackService.deleteFeedback(email);
         return ResponseEntity.status(HttpStatus.OK).body("Feedback Deleted Successfully");
    }
}
