package com.example.demo.service;

import com.example.demo.dto.response_dto.MenuAvailabilityDTO;
import com.example.demo.exception_handler.menu_exceptions.InvalidMenuException;
import com.example.demo.exception_handler.menu_exceptions.MenuAlreadyExistsException;
import com.example.demo.exception_handler.menu_exceptions.MenuNotFoundException;
import com.example.demo.models.Menu;
import com.example.demo.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    // Repository to handle CRUD operations on Menu entities.
    @Autowired
    private MenuRepository menuRepository;

    /**
     * Retrieves a menu item by its ID.
     * Throws an exception if the ID is invalid or the menu item is not found.
     *
     * @param id The ID of the menu item to retrieve.
     * @return The menu item associated with the given ID.
     * @throws MenuNotFoundException If the menu item is not found or the ID is invalid.
     */
    public Menu getMenuById(Long id){
        if(id<0){
            throw new MenuNotFoundException("Invalid Menu Id, Please Enter Valid Menu Id "+id);
        }
        Optional<Menu> menu=menuRepository.findById(id);
        return menu.orElseThrow(()->new MenuNotFoundException("No Menu Found with Id "+id));
    }

    /**
     * Retrieves a menu item by its name.
     * Throws an exception if the ID is invalid or the menu item is not found.
     *
     * @param name The name of the menu item to retrieve.
     * @return The menu item associated with the given name.
     * @throws MenuNotFoundException If the menu item is not found or the name is invalid.
     */
    public Menu getMenuByName(String name){
        if(name==null||name.trim().isEmpty()){
            throw new MenuNotFoundException("Invalid Menu name, Please Enter Valid Menu name");
        }
        Optional<Menu> menu=menuRepository.getMenuByItemName(name);
        return menu.orElseThrow(()->new MenuNotFoundException("No Menu Found with name "+name));
    }

    /**
     * Retrieves all menu items from the repository.
     * Throws an exception if no menu items are found.
     *
     * @return A list of all menu items.
     * @throws MenuNotFoundException If no menu items are found.
     */
    public List<Menu> getAllMenu(){
        List<Menu> menus=menuRepository.findAll();
        if(menus.isEmpty()){
            throw new MenuNotFoundException("No Menus Found");
        }
        return menus;
    }

    /**
     * Creates a new menu item.
     * Validates the menu data and throws an exception if it is invalid or if the menu item already exists.
     *
     * @param menu The menu item to create.
     * @return The saved menu item.
     * @throws InvalidMenuException If the menu data is invalid.
     * @throws MenuAlreadyExistsException If the menu item already exists.
     */
    public Menu createMenu(Menu menu){
        validateCreateMenu(menu);
        return menuRepository.save(menu);
    }

    /**
     * Deletes a menu item by its ID.
     * Throws an exception if the menu item is not found.
     *
     * @param id The ID of the menu item to delete.
     * @throws MenuNotFoundException If the menu item is not found.
     */
    public void deleteMenu(Long id){

        Optional<Menu> menu=menuRepository.findById(id);
        if(menu.isEmpty()){
            throw new MenuNotFoundException("No Menu Found with Id "+id);
        }
        menuRepository.deleteById(id);
    }

    /**
     * Updates an existing menu item with modified details.
     * The existing menu is overridden by the modified menu.
     *
     * @param oldId The ID of the menu item to update.
     * @param modifiedMenu The modified menu details.
     * @return The updated menu item.
     */
    public Menu updateMenu(Long oldId,Menu modifiedMenu){

        Menu existingMenu=getMenuById(oldId);
        modifiedMenu.setId(oldId);
        //over-rides old menu
        return menuRepository.save(modifiedMenu);
    }


    /**
     * Checks the availability of a menu item by its name.
     * Throws an exception if the menu item is not found.
     *
     * @param itemName The name of the menu item to check.
     * @return The menu item associated with the given name.
     * @throws MenuNotFoundException If the menu item is not found.
     */
    public MenuAvailabilityDTO checkMenuAvailability(String itemName){
        Optional<Menu> menu=menuRepository.getMenuByItemName(itemName);
        MenuAvailabilityDTO menuAvailabilityDTO=new MenuAvailabilityDTO();
        if(menu.isEmpty()){
            throw new MenuNotFoundException("No Menu Found with name "+itemName);
        }
        menuAvailabilityDTO.setItemName(menu.get().getItemName());
        menuAvailabilityDTO.setAvailable(menu.get().getAvailable());
        menuAvailabilityDTO.setAvailableQuantity(menu.get().getAvailableCount());
        return menuAvailabilityDTO;
    }

    /**
     * Validates the data for creating a new menu item.
     * Ensures that the item name, price, and availability are not null and that the price is within a valid range.
     * Throws an exception if the menu data is invalid or if the menu item already exists.
     *
     * @param menu The menu item to validate.
     * @throws InvalidMenuException If the menu data is invalid.
     * @throws MenuAlreadyExistsException If the menu item already exists.
     */
    public void validateCreateMenu(Menu menu){
        if(menu.getItemName()==null&&menu.getPrice()==null&&menu.getAvailable()==null&&menu.getAvailableCount()==null){
            throw new InvalidMenuException("ItemName ,Price and availability can;t be null to create menu");
        }else if(menu.getItemName()==null||menu.getItemName().trim().isEmpty()){
            throw new InvalidMenuException("Item Name field can't be null for Menu");
        }else if(menu.getAvailable()==null){
            throw new InvalidMenuException("Price field can't be null for Menu");
        }else if(menu.getPrice()==null||menu.getPrice()<0||menu.getPrice()>10000){
            throw new InvalidMenuException("Menu price can't be null and must be in range 1-10000");
        }else if(menu.getAvailableCount()==null||menu.getAvailableCount()<0){
            throw new InvalidMenuException("Menu availability count can't be null,And it must be >=0");
        }
        Optional<Menu> existingMenu=menuRepository.getMenuByItemName(menu.getItemName());
        if(existingMenu.isPresent()){
            throw new MenuAlreadyExistsException("Menu with itemName "+menu.getItemName()+" Already Exists");
        }
    }


}
