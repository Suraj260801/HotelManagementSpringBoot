
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
public class Complaint {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Unique identifier for the complaint.
    private Long id;
    // Name of the person submitting the complaint.
    private String name;
    // Email of the person submitting the complaint.
    private String email;
    // The content or description of the complaint.
    private String complaint;


    /**
     * Constructs a new Complaint with the specified details.
     *
     * @param id The unique identifier for the complaint.
     * @param name The name of the person submitting the complaint.
     * @param email The email of the person submitting the complaint.
     * @param complaint The content or description of the complaint.
     */
    public Complaint(Long id, String name, String email, String complaint) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.complaint = complaint;
    }

}

