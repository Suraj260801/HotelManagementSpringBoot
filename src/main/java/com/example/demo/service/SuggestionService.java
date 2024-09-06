package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.models.Suggestion;
import com.example.demo.repository.SuggestionRepository;

@Service
public class SuggestionService {

    // Repository to handle CRUD operations for Suggestion entities.
    @Autowired
    private SuggestionRepository suggestionRepository;

    /**
     * Retrieves all suggestions from the repository.
     *
     * @return A list of all suggestions.
     */
    public List<Suggestion> findAllSuggestions(){
         return suggestionRepository.findAll();
    }

    /**
     * Adds a new suggestion to the repository.
     *
     * @param suggestion The suggestion to be added.
     * @return The saved suggestion entity.
     */
    public Suggestion addSuggestion(Suggestion suggestion){
        return suggestionRepository.save(suggestion);
    }

    /**
     * Finds a suggestion by its ID.
     * If no suggestion is found, returns a default suggestion with an "Invalid Id" message.
     *
     * @param id The ID of the suggestion to find.
     * @return The suggestion associated with the given ID, or a default suggestion if not found.
     */
    public Suggestion findSuggestionById(Long id){
        return suggestionRepository.findById(id).orElse(new Suggestion(0L,"Invalid Id",""));
    }

    /**
     * Deletes a suggestion by its ID.
     *
     * @param id The ID of the suggestion to delete.
     */
    public void deleteSuggestion(Long id){
         suggestionRepository.deleteById(id);
    }
}
