package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.GroceryItem;

//DAO interface for grocery list operations (loading and saving)
public interface GroceryListDAO {

    void load(List<GroceryItem> groceryList) throws IOException;

    void save(List<GroceryItem> groceryList) throws IOException;
}