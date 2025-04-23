package com.fges.dao;

import java.io.IOException;
import java.util.List;
import com.fges.Item;
import com.fges.util.JsonHelper;

public class JSONGroceryListDAO implements GroceryListDAO {
    private final String fileName;

    public JSONGroceryListDAO(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load(List<Item> groceryList) throws IOException {
        List<Item> loadedItems = JsonHelper.loadFromFile(fileName);
        groceryList.clear();
        groceryList.addAll(loadedItems);
    }

    @Override
    public void save(List<Item> groceryList) throws IOException {
        JsonHelper.saveToFile(groceryList, fileName);
    }
}