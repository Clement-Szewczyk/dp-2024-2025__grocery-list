package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.Item;

/**
 * Data Access Object (DAO) interface for grocery list operations.
 * This interface defines the contract for classes that handle the persistence
 * of grocery list data, regardless of the storage format (CSV, JSON, etc.).
 * It follows the DAO design pattern to separate the data persistence logic
 * from the business logic of the application.
 *
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
public interface GroceryListDAO {
    /**
     * Loads grocery items from persistent storage into the provided list.
     * Implementations should clear the list and then populate it with items
     * from the data source.
     *
     * @param groceryList The list to be populated with loaded items
     * @throws IOException If an I/O error occurs during the loading process
     */
    void load(List<Item> groceryList) throws IOException;

    /**
     * Saves the provided list of grocery items to persistent storage.
     * Implementations should overwrite any existing data in the storage.
     *
     * @param groceryList The list of items to be saved
     * @throws IOException If an I/O error occurs during the saving process
     */
    void save(List<Item> groceryList) throws IOException;
}