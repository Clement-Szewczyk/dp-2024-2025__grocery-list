package com.fges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fges.dao.GroceryListDAO;

/**
 * Manager class for handling grocery list operations.
 * This class manages a list of grocery items and provides methods for adding, removing,
 * and displaying items. It also handles persistence operations through a DAO object.
 */
public class GroceryListManager {
    private final GroceryListDAO dao;

    private List<GroceryItem> groceryList;

    public GroceryListManager(GroceryListDAO dao) {
        this.dao = dao;
        this.groceryList = new ArrayList<>();
    }

    /**
     * Initializes the manager by loading the grocery list from storage.
     */
    public void initialize() {
        loadFromFile();
    }

    public void loadFromFile() {
        try {
            dao.load(groceryList);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try {
            dao.save(groceryList);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void add(String itemName, int quantity, String category) {
        boolean itemFound = false;
        // Check if the item already exists in the list
        for (GroceryItem groceryItem : groceryList) {
            if (groceryItem.getName().equalsIgnoreCase(itemName) &&
                    groceryItem.getCategory().equalsIgnoreCase(category)) {
                // If found, increase its quantity
                groceryItem.quantity += quantity;
                itemFound = true;
                break;
            }
        }
        // If not found, add as a new item
        if (!itemFound) {
            groceryList.add(new GroceryItem(itemName, quantity, category));
        }
    }

    public void add(String itemName, int quantity) {
        add(itemName, quantity, "default");
    }

    public void add(String itemName) {
        add(itemName, 1, "default");
    }

    public void remove(String itemName, int quantity) {
        if (quantity <= 0) {
            // If quantity is zero or negative, remove all matching items
            groceryList.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        } else {
            // Otherwise, decrease the quantity of matching items
            groceryList.forEach(item -> {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    item.quantity -= quantity;
                }
            });
            // Remove any items whose quantity has become zero or negative
            groceryList.removeIf(item -> item.quantity <= 0);
        }
    }

    public void remove(String itemName) {
        remove(itemName, 0);
    }

    public void list() {
        if (groceryList.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            for (GroceryItem groceryItem : groceryList) {
                System.out.println(groceryItem.getName() + " : " + groceryItem.quantity +
                        " [Category: " + groceryItem.getCategory() + "]");
            }
        }
    }

    public List<GroceryItem> getGroceryList() {
        return groceryList;
    }


}