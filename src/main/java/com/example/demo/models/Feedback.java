package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Unique identifier for the feedback.
    private Long id;
    // Name of the person submitting the feedback.
    private String name;
    // The content or description of the feedback.
    private String feedback;
    // Email of the person submitting the feedback.
    private String email;

    /**
     * Constructs a new Feedback with the specified details.
     *
     * @param id The unique identifier for the feedback.
     * @param name The name of the person submitting the feedback.
     * @param email The email of the person submitting the feedback.
     * @param feedback The content or description of the feedback.
     */
    public Feedback(Long id, String name, String email, String feedback) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.feedback = feedback;
    }

}
