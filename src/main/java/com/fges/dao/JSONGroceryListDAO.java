package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.Item;
import com.fges.JSON;

public class JSONGroceryListDAO implements GroceryListDAO {
    private final String fileName;
    private final JSON jsonHandler;
    
    public JSONGroceryListDAO(String fileName) {
        this.fileName = fileName;
        this.jsonHandler = new JSON();
    }
    
    @Override
    public void load(List<Item> groceryList) throws IOException {
        jsonHandler.load(groceryList, fileName);
    }
    
    @Override
    public void save(List<Item> groceryList) throws IOException {
        jsonHandler.save(groceryList, fileName);
    }
}