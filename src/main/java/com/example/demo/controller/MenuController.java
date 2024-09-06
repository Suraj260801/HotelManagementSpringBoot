package com.example.demo.controller;

import com.example.demo.dto.response_dto.MenuAvailabilityDTO;
import com.example.demo.models.Menu;
import com.example.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menu")
public class MenuController {


    // Injects the MenuService to handle business logic related to menus
    @Autowired
    private MenuService menuService;


    /**
     * Retrieves a specific menu item by its name.
     * @param name The name of the menu item to retrieve.
     * @return ResponseEntity containing the Menu object if found.
     */
    @GetMapping("/{name}")
    public ResponseEntity<Object> getMenuByName(@PathVariable String name){
        Menu menu=menuService.getMenuByName(name);
        return ResponseEntity.ok(menu);
    }

    /**
     * Retrieves a list of all menu items.
     * @return ResponseEntity containing the list of all Menu objects.
     */
    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenu(){
            List<Menu> menus=menuService.getAllMenu();
            return ResponseEntity.ok(menus);
    }

    /**
     * Checks if a menu item is available by its name.
     * @param itemName The name of the menu item to check availability.
     * @return ResponseEntity containing the Menu object if available.
     */
    @GetMapping("/isAvailable/{itemName}")
    public ResponseEntity<Object> isMenuAvailable(@PathVariable String itemName){
            MenuAvailabilityDTO availabilityDTO=menuService.checkMenuAvailability(itemName);
            return ResponseEntity.ok(availabilityDTO);
    }

    /**
     * Adds a new menu item to the menu.
     * @param menu The Menu object representing the new item to be added.
     * @return ResponseEntity containing the saved Menu object.
     */
    @PostMapping
    public ResponseEntity<Menu> addNewMenu(@RequestBody Menu menu){
            Menu savedMenu=menuService.createMenu(menu);
            return ResponseEntity.ok(savedMenu);
    }

    /**
     * Deletes a menu item by its ID.
     * @param id The ID of the menu item to delete.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id){
            menuService.deleteMenu(id);
            return ResponseEntity.ok("Menu Item Deleted Successfully");
    }

    /**
     * Updates an existing menu item with a given ID.
     * @param menu The updated Menu object.
     * @param id The ID of the menu item to update.
     * @return ResponseEntity containing the updated Menu object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@RequestBody Menu menu,@PathVariable Long id){
            Menu updatedMenu=menuService.updateMenu(id,menu);
            return ResponseEntity.ok(updatedMenu);
    }


}
