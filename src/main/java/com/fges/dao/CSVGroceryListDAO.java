package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.CSV;
import com.fges.Item;

public class CSVGroceryListDAO implements GroceryListDAO {
    private final String fileName;
    private final CSV csvHandler;
    
    public CSVGroceryListDAO(String fileName) {
        this.fileName = fileName;
        this.csvHandler = new CSV();
    }
    
    @Override
    public void load(List<Item> groceryList) throws IOException {
        csvHandler.load(groceryList, fileName);
    }
    
    @Override
    public void save(List<Item> groceryList) throws IOException {
        csvHandler.save(groceryList, fileName);
    }
}