package com.fges.dao;

public class GroceryListDAOFactory {
    public static GroceryListDAO createDAO(String fileName, String format) {
        if (format.equalsIgnoreCase("csv")) {
            return new CSVGroceryListDAO(fileName);
        } else {
            return new JSONGroceryListDAO(fileName); // JSON par défaut
        }
    }
}