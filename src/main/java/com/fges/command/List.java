package com.fges.command;

import com.fges.GroceryListManager;

public class List implements Command {
    @Override
    public int execute(String[] args, GroceryListManager manager, String category) {
        manager.list();
        manager.saveToFile();
        return 0;
    }
}