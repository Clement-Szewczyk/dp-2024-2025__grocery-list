package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.GroceryItem;
import com.fges.util.CsvHelper;

/**
 * Implementation of the GroceryListDAO interface that handles
  persistence of grocery list data using a CSV file.

 * This class delegates the actual CSV parsing and writing logic to the
 CsvHelper utility class.
 */
public class CSVGroceryListDAO implements GroceryListDAO {
    
    private final String fileName;

    public CSVGroceryListDAO(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void load(List<GroceryItem> groceryList) throws IOException {
        List<GroceryItem> loadedGroceryItems = CsvHelper.loadFromFile(fileName);
        groceryList.clear();
        groceryList.addAll(loadedGroceryItems);
    }

    @Override
    public void save(List<GroceryItem> groceryList) throws IOException {
        CsvHelper.saveToFile(groceryList, fileName);
    }
}
