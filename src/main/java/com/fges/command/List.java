package com.fges.command;

import com.fges.GroceryListManager;

public class List implements Command {
    @Override
    public int execute(GroceryListManager manager) {
        manager.list();
        manager.saveToFile();
        return 0;
    }
}