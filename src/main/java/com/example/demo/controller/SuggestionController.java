package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Suggestion;
import com.example.demo.service.SuggestionService;

@RestController
@RequestMapping("/suggestions")
public class SuggestionController {

    // Injects the SuggestionService to handle business logic related to suggestions
    @Autowired
    private SuggestionService suggestionService;

    /**
     * Retrieves a list of all suggestions.
     * @return A list of Suggestion objects.
     */
    @GetMapping
    public List<Suggestion> getAllSuggestions(){
        return suggestionService.findAllSuggestions();
    }

    /**
     * Adds a new suggestion.
     * @param suggestion The Suggestion object to be added.
     * @return The newly added Suggestion object.
     */
    @PostMapping
    public Suggestion addSuggesion(@RequestBody Suggestion suggestion){
           return suggestionService.addSuggestion(suggestion);
    }


    /**
     * Retrieves a specific suggestion by its ID.
     * @param id The ID of the suggestion to retrieve.
     * @return The Suggestion object if found.
     */
    @GetMapping("/{id}")
    public Suggestion getSuggestionById(@PathVariable Long id){
          return suggestionService.findSuggestionById(id);
    }

    /**
     * Updates an existing suggestion by its ID.
     * @param suggestion The updated Suggestion object.
     * @param id The ID of the suggestion to update.
     * @return The updated Suggestion object.
     */
    @PostMapping("/{id}")
    public Suggestion updateSuggestion(@RequestBody Suggestion suggestion,@PathVariable Long id){
         suggestion.setId(id);
         return suggestionService.addSuggestion(suggestion);
    }

    /**
     * Deletes a suggestion by its ID.
     * @param id The ID of the suggestion to delete.
     */
    @DeleteMapping("/{id}/delete")
    public void deleteSuggestion(@PathVariable Long id){
          suggestionService.deleteSuggestion(id);
    }
}
