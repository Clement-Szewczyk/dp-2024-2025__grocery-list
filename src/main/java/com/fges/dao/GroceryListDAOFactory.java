package com.fges.dao;

/**
 * Factory class for creating appropriate GroceryListDAO implementations.
 * This class implements the Factory design pattern to centralize the creation
 * of DAO objects based on the specified file format.
 * It encapsulates the instantiation logic, providing a cleaner API for clients.
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
public class GroceryListDAOFactory {
    /**
     * Creates and returns a GroceryListDAO implementation based on the specified format.
     * Currently supports CSV and JSON formats, with JSON being the default.
     *
     * @param fileName The name of the file to be used for data storage
     * @param format The format of the file ("csv" for CSV format, any other value for JSON)
     * @return A GroceryListDAO implementation appropriate for the specified format
     */
    public static GroceryListDAO createDAO(String fileName, String format) {
        // Create CSV DAO if format is CSV (case-insensitive)
        if (format.equalsIgnoreCase("csv")) {
            return new CSVGroceryListDAO(fileName);
        } else {
            // Default to JSON DAO for any other format
            return new JSONGroceryListDAO(fileName);
        }
    }
}