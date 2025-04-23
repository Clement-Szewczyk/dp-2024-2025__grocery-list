package com.fges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fges.dao.GroceryListDAO;

public class GroceryListManager {
    private final GroceryListDAO dao;
    private List<Item> groceryList;

    public GroceryListManager(GroceryListDAO dao) {
        this.dao = dao;
        this.groceryList = new ArrayList<>();
    }

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
        for (Item item : groceryList) {
            if (item.getName().equalsIgnoreCase(itemName) &&
                    item.getCategory().equalsIgnoreCase(category)) {
                item.quantity += quantity;
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            groceryList.add(new Item(itemName, quantity, category));
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
            groceryList.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        } else {
            groceryList.forEach(item -> {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    item.quantity -= quantity;
                }
            });
            groceryList.removeIf(item -> item.quantity <= 0);
        }
    }

    public void remove(String itemName) {
        remove(itemName, 0);
    }

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

    public List<Item> getGroceryList() {
        return groceryList;
    }
}