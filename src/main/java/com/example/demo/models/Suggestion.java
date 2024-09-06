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
public class Suggestion {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    // Unique identifier for the suggestion.
    private Long id;
    // Name of the person providing the suggestion
    private String name;
    // The content of the suggestion provided by the user.
    private String suggestion;


    /**
     * Constructs a new Suggestion with the specified details.
     *
     * @param id The unique identifier for the suggestion.
     * @param name The name of the person providing the suggestion.
     * @param suggestion The content of the suggestion.
     */
    public Suggestion(Long id, String name, String suggestion) {
        this.id = id;
        this.name = name;
        this.suggestion = suggestion;
    }
}
