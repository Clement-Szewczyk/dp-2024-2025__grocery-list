package com.fges;

/**
 * Represents an item in a grocery list with a name, quantity, and category.
 * It provides getters and setters for accessing and modifying item properties.

 */
public class GroceryItem {
    public String name;

    public int quantity;
    private String category;

    /**
     Constructs a new GroceryItem with the specified name, quantity, and category.
     */
    public GroceryItem(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}