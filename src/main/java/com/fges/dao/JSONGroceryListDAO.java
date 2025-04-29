package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.GroceryItem;
import com.fges.util.JsonHelper;

/**
 * JSON implementation of the GroceryListDAO interface.
 * This class provides methods to load and save grocery list data in JSON format.
 * It utilizes the JsonHelper utility class to perform the actual file operations.
 */
public class JSONGroceryListDAO implements GroceryListDAO {

    private final String fileName;

    public JSONGroceryListDAO(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load(List<GroceryItem> groceryList) throws IOException {
        List<GroceryItem> loadedGroceryItems = JsonHelper.loadFromFile(fileName);
        groceryList.clear();
        groceryList.addAll(loadedGroceryItems);
    }

    @Override
    public void save(List<GroceryItem> groceryList) throws IOException {
        JsonHelper.saveToFile(groceryList, fileName);
    }
}
