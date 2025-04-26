package com.fges;

/**
 * Represents an item in a grocery list with a name, quantity, and category.
 * This class serves as the core data model for grocery items in the application.
 * It provides getters and setters for accessing and modifying item properties.
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
public class GroceryItem {
    /**
     * The name of the grocery item
     */
    public String name;

    /**
     * The quantity of the grocery item
     */
    public int quantity;

    /**
     * The category of the grocery item (private with accessor methods)
     */
    private String category;

    /**
     * Constructs a new GroceryItem with the specified name, quantity, and category.
     *
     * @param name The name of the grocery item
     * @param quantity The quantity of the grocery item
     * @param category The category of the grocery item
     */
    public GroceryItem(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    /**
     * Returns the name of the grocery item.
     *
     * @return The name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the grocery item.
     * Required setter method for data binding.
     *
     * @param name The new name for the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the category of the grocery item.
     *
     * @return The category of the item
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the grocery item.
     *
     * @param category The new category for the item
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the quantity of the grocery item.
     *
     * @return The quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the grocery item.
     * Required setter method for data binding.
     *
     * @param quantity The new quantity for the item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}