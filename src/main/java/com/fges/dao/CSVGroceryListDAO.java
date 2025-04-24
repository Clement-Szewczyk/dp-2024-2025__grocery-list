package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.CSV;
import com.fges.Item;

/**
 * CSV implementation of the GroceryListDAO interface.
 * This class provides methods to load and save grocery list data in CSV format.
 * It utilizes the CSV handler class to perform the actual file operations.
 *
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
public class CSVGroceryListDAO implements GroceryListDAO {
    /**
     * The name of the file where the grocery list data is stored
     */
    private final String fileName;

    /**
     * The CSV handler that performs the actual CSV file operations
     */
    private final CSV csvHandler;

    /**
     * Constructs a new CSVGroceryListDAO with the specified file name.
     * Initializes the CSV handler for file operations.
     *
     * @param fileName The name of the CSV file to use for data storage
     */
    public CSVGroceryListDAO(String fileName) {
        this.fileName = fileName;
        this.csvHandler = new CSV();
    }

    /**
     * Loads the grocery list from the CSV file.
     * Delegates to the CSV handler to perform the actual file reading and parsing.
     *
     * @param groceryList The list to be populated with items from the CSV file
     * @throws IOException If an I/O error occurs during the file reading process
     */
    @Override
    public void load(List<Item> groceryList) throws IOException {
        csvHandler.load(groceryList, fileName);
    }

    /**
     * Saves the grocery list to the CSV file.
     * Delegates to the CSV handler to perform the actual file writing.
     *
     * @param groceryList The list of items to be saved to the CSV file
     * @throws IOException If an I/O error occurs during the file writing process
     */
    @Override
    public void save(List<Item> groceryList) throws IOException {
        csvHandler.save(groceryList, fileName);
    }
}