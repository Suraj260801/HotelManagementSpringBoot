package com.example.demo.repository;

import com.example.demo.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Menu} entities.
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations for Menu entities.
 * </p>
 */
public interface MenuRepository extends JpaRepository<Menu,Long> {

    /**
     * Retrieves a {@link Menu} entity by its item name.
     *
     * @param itemName the name of the menu item.
     * @return an {@link Optional} containing the {@link Menu} entity if found, otherwise empty.
     */
     Optional<Menu> getMenuByItemName(String itemName);

}
