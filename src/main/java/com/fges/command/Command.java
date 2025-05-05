package com.fges.command;

import com.fges.GroceryListManager;

public interface Command {
    // Version simplifiée utilisant CommandOption
    int execute(GroceryListManager manager);
}