package com.fges.command;

import com.fges.GroceryListManager;

public interface Command {
    int execute(GroceryListManager manager);
}