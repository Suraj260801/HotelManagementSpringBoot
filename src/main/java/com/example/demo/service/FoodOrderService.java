package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.dto.response_dto.FoodItemDTO;
import com.example.demo.dto.response_dto.FoodOrderAcceptedDTO;
import com.example.demo.exception_handler.food_exceptions.FoodItemNotAvailableException;
import com.example.demo.exception_handler.food_exceptions.FoodNotFoundException;
import com.example.demo.exception_handler.food_order_exceptions.FoodOrderNotFoundException;
import com.example.demo.exception_handler.food_order_exceptions.InvalidFoodOrderException;
import com.example.demo.models.Bill;
import com.example.demo.models.Food;
import com.example.demo.models.FoodOrder;
import com.example.demo.models.Menu;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.FoodOrderRepository;
import com.example.demo.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderService {

    // Repository to handle CRUD operations on FoodOrder entities.
    @Autowired
    private FoodOrderRepository foodRepository;

    // Repository to retrieve food items from the Menu.
    @Autowired
    private MenuRepository menuRepository;


    // Service to update the menu items availability when food order is made
    @Autowired
    private MenuService menuService;


    /**
     * Retrieves all food orders from the repository.
     * Throws an exception if no food orders are found.
     *
     * @return List of all food orders.
     * @throws FoodOrderNotFoundException If no food orders are found.
     */
    public List<FoodOrderAcceptedDTO> findAllFoods() {
        List<FoodOrder> foodOrders=foodRepository.findAll();
        if(foodOrders.isEmpty()){
            throw new FoodOrderNotFoundException("No Food Orders Found");
        }
        List<FoodOrderAcceptedDTO> foodOrderAcceptedDTOList=new ArrayList<>();
        for(FoodOrder foodOrder:foodOrders){
            List<FoodItemDTO> foodItemDTOList=new ArrayList<>();
            for(Food f: foodOrder.getFoodItems()){
                foodItemDTOList.add(new FoodItemDTO(f.getFoodName(),f.getQuantity().intValue()));
            }
            foodOrderAcceptedDTOList.add(new FoodOrderAcceptedDTO(foodOrder.getId(), foodOrder.getCustomerName(), LocalDateTime.now(),foodItemDTOList));
        }
        return foodOrderAcceptedDTOList;
    }

    /**
     * Saves a new food order to the repository after validating the order.
     * It calculates the total price of the order and creates a bill.
     *
     * @param rawFoodOrder The food order to be saved.
     * @return The saved food order with the processed food items and bill.
     * @throws InvalidFoodOrderException If the order is invalid (e.g., missing customer name or food items).
     * @throws FoodItemNotAvailableException If any food items are not available in the menu.
     */
    public FoodOrderAcceptedDTO saveFood(FoodOrder rawFoodOrder) {
        List<Food> foods=rawFoodOrder.getFoodItems();
        List<Food> processedFoods=new ArrayList<>();
        // Validate the food order's general properties.
        validateFoodOrder(rawFoodOrder);
        // Validate that the food items are available in the menu.
        validateFoodItems(rawFoodOrder);
        double totalAmount=0;
        // Process each food item and calculate the total amount.
        for(Food f:foods){
            List<Menu> menus=menuRepository.findAll();
            for(Menu m:menus){
                if(m.getItemName().equalsIgnoreCase(f.getFoodName())){
                    totalAmount=m.getPrice()*f.getQuantity()+totalAmount;
                    Food food1=new Food(m,f.getFoodName(),f.getQuantity());
                    processedFoods.add(food1);
                }
            }

        }
        // Create a bill with the total amount and associate it with the food order.
        Bill bill=new Bill(totalAmount);
        FoodOrder processedFoodOrder=new FoodOrder(processedFoods,rawFoodOrder.getCustomerName(),bill);
        bill.setFoodOrder(processedFoodOrder);
        foodRepository.save(processedFoodOrder);

        FoodOrderAcceptedDTO foodOrderAcceptedDTO=new FoodOrderAcceptedDTO();
        foodOrderAcceptedDTO.setId(processedFoodOrder.getId());
        foodOrderAcceptedDTO.setOrderDate(LocalDateTime.now());
        foodOrderAcceptedDTO.setCustomerName(processedFoodOrder.getCustomerName());
        List<FoodItemDTO> foodItemDTOList=new ArrayList<>();
        for(Food f: processedFoodOrder.getFoodItems()){
            foodItemDTOList.add(new FoodItemDTO(f.getFoodName(),f.getQuantity().intValue()));
        }
        foodOrderAcceptedDTO.setFoodItems(foodItemDTOList);
        return foodOrderAcceptedDTO;
    }

    /**
     * Finds a food order by its Customer Name.
     * Throws an exception if the Name is invalid or the food order is not found.
     *
     * @param name The Name of the food order.
     * @return The food order associated with the given Name.
     * @throws FoodOrderNotFoundException If the food order is not found or the Name is invalid.
     */
    public FoodOrderAcceptedDTO findFoodByCustomerName(String name) {
        if(name==null||name.trim().isEmpty()){
            throw new FoodOrderNotFoundException("No Food Order Found, Invalid Customer Name "+name);
        }
        Optional<FoodOrder> order=foodRepository.getFoodOrderByCustomerName(name);
        if(order.isEmpty()){
            throw new FoodOrderNotFoundException("No Food Order Found, Invalid Customer Name "+name);
        }
        FoodOrderAcceptedDTO foodOrderAcceptedDTO=new FoodOrderAcceptedDTO();
        foodOrderAcceptedDTO.setId(order.get().getId());
        foodOrderAcceptedDTO.setCustomerName(order.get().getCustomerName());
        foodOrderAcceptedDTO.setOrderDate(LocalDateTime.now());
        List<FoodItemDTO> foodItemDTOList=new ArrayList<>();
        for (Food f:order.get().getFoodItems()){
            foodItemDTOList.add(new FoodItemDTO(f.getFoodName(),f.getQuantity().intValue()));
        }
        foodOrderAcceptedDTO.setFoodItems(foodItemDTOList);
        return foodOrderAcceptedDTO;
    }


    /**
     * Finds a food order by the ID.
     * Throws an exception if no food order is found for the given ID.
     *
     * @param id The ID of the customer.
     * @return The food order associated with the ID.
     * @throws FoodOrderNotFoundException If no food order is found for the given ID.
     */
    public FoodOrder findFoodById(Long id){
        if(id<0){
            throw new FoodNotFoundException("Invalid Food Order Id, Please Enter valid food id");
        }
        Optional<FoodOrder> order=foodRepository.findById(id);
        return order.orElseThrow(()->new FoodOrderNotFoundException("No Food Order Found with Id "+id));
    }

    /**
     * Cancels (deletes) a food order by its ID.
     *
     * @param id The ID of the food order to cancel.
     */
    public void cancelFoodOrder(Long id) {
        FoodOrder existingFoodOrder=findFoodById(id);
        foodRepository.deleteById(existingFoodOrder.getId());
    }

    /**
     * Validates the general properties of a food order.
     * Throws an exception if the customer name or food items are missing.
     *
     * @param foodOrder The food order to validate.
     * @throws InvalidFoodOrderException If the order is missing required fields.
     */
    public void validateFoodOrder(FoodOrder foodOrder){
        if (foodOrder.getFoodItems()==null&&foodOrder.getCustomerName()==null){
            throw new InvalidFoodOrderException("Customer Name and food Items Can't be null for Food Order");
        }else if(foodOrder.getCustomerName()==null||foodOrder.getCustomerName().trim().isEmpty()){
            throw new InvalidFoodOrderException("Customer Name can't be null for Order");
        }else if(foodOrder.getFoodItems()==null||foodOrder.getFoodItems().isEmpty()){
            throw new InvalidFoodOrderException("Food Items Can't be null for Food Order");
        }
    }

    /**
     * Validates that the food items in the order are available in the menu.
     * Throws an exception if any food items are not available.
     *
     * @param foodOrder The food order containing the items to validate.
     * @throws FoodItemNotAvailableException If any food items are not available in the menu.
     */
    public void validateFoodItems(FoodOrder foodOrder){
        List<Food> foods=foodOrder.getFoodItems();
        List<String> unavailableFoods=new ArrayList<>();
        HashMap<String,Integer> availableFoods=new HashMap<>();
        HashMap<String,Integer> orderedFoods=new HashMap<>();
        for(Food f:foods) {
            orderedFoods.put(f.getFoodName(),f.getQuantity().intValue());
            Optional<Menu> m = menuRepository.getMenuByItemName(f.getFoodName());
            if (m.isEmpty()) {
                unavailableFoods.add(f.getFoodName());
            }else{
                if(m.get().getAvailableCount()<f.getQuantity()){
                    unavailableFoods.add(f.getFoodName());
                }
                availableFoods.put(m.get().getItemName(),m.get().getAvailableCount());
            }
        }
        if(!unavailableFoods.isEmpty()){
            throw new FoodItemNotAvailableException("Sorry for Inconvenience, Requested Food Items are not available in Full Quantity",orderedFoods,availableFoods);
        }

        for(Food f:foods){
            Optional<Menu> m=menuRepository.getMenuByItemName(f.getFoodName());
            long newMenuAvailableCount=m.get().getAvailableCount()-f.getQuantity();
            m.get().setAvailableCount(Math.toIntExact(newMenuAvailableCount));
            Boolean isMenuItemsAvailable=newMenuAvailableCount>0;
            m.get().setAvailable(isMenuItemsAvailable);
            availableFoods.put(m.get().getItemName(),m.get().getAvailableCount());
            menuService.updateMenu(m.get().getId(),m.get());
        }

    }

}
