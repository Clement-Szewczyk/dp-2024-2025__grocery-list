package com.fges.dao;

/**
 * Factory class for creating appropriate GroceryListDAO implementations.
 * This class implements the Factory design pattern to centralize the creation
 of DAO objects based on the specified file format.
 */
public class GroceryListDAOFactory {
    /**
     * Creates and returns a GroceryListDAO implementation based on the specified format.
     * Currently supports CSV and JSON formats, with JSON being the default.
     */
    public static GroceryListDAO createDAO(String fileName, String format) {
        // Create CSV DAO if format is CSV
        if (format.equalsIgnoreCase("csv")) {
            return new CSVGroceryListDAO(fileName);
        } else {
            // Default to JSON DAO for any other format
            return new JSONGroceryListDAO(fileName);
        }
    }
}