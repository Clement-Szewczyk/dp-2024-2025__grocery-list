package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.Item;
import com.fges.util.CsvHelper;

public class CSVGroceryListDAO implements GroceryListDAO {
    private final String fileName;

    public CSVGroceryListDAO(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load(List<Item> groceryList) throws IOException {
        List<Item> loadedItems = CsvHelper.loadFromFile(fileName);
        groceryList.clear();
        groceryList.addAll(loadedItems);
    }

    @Override
    public void save(List<Item> groceryList) throws IOException {
        CsvHelper.saveToFile(groceryList, fileName);
    }
}