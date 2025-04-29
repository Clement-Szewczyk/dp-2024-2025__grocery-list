package com.fges.command;

import com.fges.GroceryListManager;

public class Remove implements Command {
    @Override
    public int execute(String[] args, GroceryListManager manager, String category) {
        try {
            if (args.length < 2) {
                System.err.println("Missing arguments for remove command");
                return 1;
            }

            String itemName = args[1];

            if (args.length >= 3) {
                int quantity = Integer.parseInt(args[2]);
                manager.remove(itemName, quantity);
            } else {
                manager.remove(itemName);
            }

            manager.saveToFile();
            return 0;
        } catch (NumberFormatException e) {
            System.err.println("Invalid quantity format");
            return 1;
        }
    }
}