package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.Item;

public interface GroceryListDAO {
    void load(List<Item> groceryList) throws IOException;
    void save(List<Item> groceryList) throws IOException;
}