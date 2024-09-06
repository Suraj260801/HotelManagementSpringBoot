
package com.example.demo.controller;

import com.example.demo.models.Complaint;
import com.example.demo.service.ComplaintService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    // Injects the ComplaintService to handle business logic related to complaints
    @Autowired
    private ComplaintService complaintService;

    /**
     * Retrieves all complaints.
     * @return List of all Complaint objects.
     */
    @GetMapping
    public List<Complaint> getAllComplaints() {
       return complaintService.findAllComplaints();
    }

    /**
     * Retrieves a complaint by its ID.
     * @param id The ID of the complaint.
     * @return The Complaint object if found.
     */
    @GetMapping("/{id}")
    public Complaint getComplaintById(@PathVariable Long id) {
        return complaintService.findComplaintById(id);
    }


    /**
     * Creates a new complaint.
     * @param complaint The Complaint object to be created.
     * @return The created Complaint object.
     */
    @PostMapping
    public Complaint createComplaint(@RequestBody Complaint complaint) {
        return complaintService.saveComplaint(complaint);
    }


    /**
     * Updates an existing complaint with a given ID.
     * @param id The ID of the complaint to be updated.
     * @param complaint The updated Complaint object.
     * @return The updated Complaint object.
     */
    @PostMapping("/{id}")
    public Complaint updateComplaint(@PathVariable Long id, @RequestBody Complaint complaint) {
        complaint.setId(id);
        return complaintService.saveComplaint(complaint);
    }


    /**
     * Deletes a complaint by its ID.
     * @param id The ID of the complaint to be deleted.
     */
    @DeleteMapping("/{id}/delete")
    public void deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
    }
}

