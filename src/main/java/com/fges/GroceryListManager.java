package com.fges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fges.dao.GroceryListDAO;

/**
 * Manager class for handling grocery list operations.
 * This class manages a list of grocery items and provides methods for adding, removing,
 * and displaying items. It also handles persistence operations through a DAO object.
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
public class GroceryListManager {
    /**
     * Data Access Object for loading and saving the grocery list
     */
    private final GroceryListDAO dao;

    /**
     * The list of grocery items managed by this class
     */
    private List<Item> groceryList;

    /**
     * Constructs a GroceryListManager with the specified DAO.
     *
     * @param dao The Data Access Object to use for persistence operations
     */
    public GroceryListManager(GroceryListDAO dao) {
        this.dao = dao;
        this.groceryList = new ArrayList<>();
    }

    /**
     * Initializes the manager by loading the grocery list from storage.
     * This should be called once after creating the manager.
     */
    public void initialize() {
        loadFromFile();
    }

    /**
     * Loads the grocery list from the persistent storage using the DAO.
     * Any existing items in the list will be replaced.
     */
    public void loadFromFile() {
        try {
            dao.load(groceryList);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Saves the current grocery list to persistent storage using the DAO.
     * This should be called after any operation that modifies the list.
     */
    public void saveToFile() {
        try {
            dao.save(groceryList);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Adds an item to the grocery list with the specified name, quantity, and category.
     * If an item with the same name and category already exists, its quantity is increased.
     *
     * @param itemName The name of the item to add
     * @param quantity The quantity of the item to add
     * @param category The category of the item
     */
    public void add(String itemName, int quantity, String category) {
        boolean itemFound = false;
        // Check if the item already exists in the list
        for (Item item : groceryList) {
            if (item.getName().equalsIgnoreCase(itemName) &&
                    item.getCategory().equalsIgnoreCase(category)) {
                // If found, increase its quantity
                item.quantity += quantity;
                itemFound = true;
                break;
            }
        }
        // If not found, add as a new item
        if (!itemFound) {
            groceryList.add(new Item(itemName, quantity, category));
        }
    }

    /**
     * Adds an item to the grocery list with the specified name and quantity.
     * Uses the default category.
     *
     * @param itemName The name of the item to add
     * @param quantity The quantity of the item to add
     */
    public void add(String itemName, int quantity) {
        add(itemName, quantity, "default");
    }

    /**
     * Adds a single unit of an item to the grocery list with the specified name.
     * Uses the default category.
     *
     * @param itemName The name of the item to add
     */
    public void add(String itemName) {
        add(itemName, 1, "default");
    }

    /**
     * Removes a specified quantity of an item from the grocery list.
     * If the quantity becomes zero or negative, the item is removed entirely.
     * If the quantity parameter is zero or negative, all items with the matching name are removed.
     *
     * @param itemName The name of the item to remove
     * @param quantity The quantity to remove, or 0 to remove all items with the matching name
     */
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

    /**
     * Removes all items with the specified name from the grocery list.
     * This is a convenience method that calls remove(itemName, 0).
     *
     * @param itemName The name of the item to remove
     */
    public void remove(String itemName) {
        remove(itemName, 0);
    }

    /**
     * Displays the current grocery list to the console.
     * If the list is empty, displays a message indicating that.
     */
    public void list() {
        if (groceryList.isEmpty()) {
            System.out.println("La liste est vide.");
        } else {
            for (Item item : groceryList) {
                System.out.println(item.getName() + " : " + item.quantity +
                        " [Catégorie: " + item.getCategory() + "]");
            }
        }
    }

    /**
     * Returns the current grocery list.
     *
     * @return The list of Item objects
     */
    public List<Item> getGroceryList() {
        return groceryList;
    }
}