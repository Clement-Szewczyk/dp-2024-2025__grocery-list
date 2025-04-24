package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.Item;
import com.fges.util.JsonHelper;

/**
 * JSON implementation of the GroceryListDAO interface.
 * This class provides methods to load and save grocery list data in JSON format.
 * It utilizes the JsonHelper utility class to perform the actual file operations.
 *
 * This implementation is particularly useful for structured and readable storage.
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
public class JSONGroceryListDAO implements GroceryListDAO {
    /**
     * The name of the file where the grocery list data is stored
     */
    private final String fileName;

    /**
     * Constructs a new JSONGroceryListDAO with the specified file name.
     *
     * @param fileName The name of the JSON file to use for data storage
     */
    public JSONGroceryListDAO(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Loads the grocery list from the JSON file.
     * Delegates to the JsonHelper class to perform the actual file reading and parsing.
     * Clears the existing list before loading new items to ensure no duplicates.
     *
     * @param groceryList The list to be populated with items from the JSON file
     * @throws IOException If an I/O error occurs during the file reading process
     */
    @Override
    public void load(List<Item> groceryList) throws IOException {
        List<Item> loadedItems = JsonHelper.loadFromFile(fileName);
        groceryList.clear();
        groceryList.addAll(loadedItems);
    }

    /**
     * Saves the grocery list to the JSON file.
     * Delegates to the JsonHelper class to perform the actual file writing.
     *
     * @param groceryList The list of items to be saved to the JSON file
     * @throws IOException If an I/O error occurs during the file writing process
     */
    @Override
    public void save(List<Item> groceryList) throws IOException {
        JsonHelper.saveToFile(groceryList, fileName);
    }
}
