package com.fges.dao;

public class GroceryListDAOFactory {
    public static GroceryListDAO createDAO(String fileName) {
        if (fileName.endsWith(".csv")) {
            return new CSVGroceryListDAO(fileName);
        } else if (fileName.endsWith(".json")) {
            return new JSONGroceryListDAO(fileName);
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + fileName);
        }
    }
}