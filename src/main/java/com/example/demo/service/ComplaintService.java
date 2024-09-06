package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Complaint;
import com.example.demo.repository.ComplaintRepository;

@Service
public class ComplaintService {

    // Repository to interact with Complaint entities in the database
    @Autowired
    private ComplaintRepository complaintRepository;


    /**
     * Retrieves all complaints from the repository.
     *
     * @return A list of all complaints.
     */
    public List<Complaint> findAllComplaints() {
        return complaintRepository.findAll();
    }


    /**
     * Saves a new or updated complaint to the repository.
     *
     * @param complaint The complaint entity to be saved.
     * @return The saved complaint entity.
     */
    public Complaint saveComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }


    /**
     * Retrieves a complaint by its ID. If the complaint is not found, returns a default complaint with an "Invalid Id" message.
     *
     * @param id The ID of the complaint to retrieve.
     * @return The found complaint, or a default complaint if not found.
     */
    public Complaint findComplaintById(Long id) {
        return complaintRepository.findById(id).orElse(new Complaint(0L,"Invalid Id","",""));
    }


    /**
     * Deletes a complaint by its ID.
     *
     * @param id The ID of the complaint to delete.
     */
    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }
}
