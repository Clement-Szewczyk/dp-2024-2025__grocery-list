package com.fges.command;

import com.fges.GroceryListManager;

public class Add implements Command {
    @Override
    public int execute(GroceryListManager manager) {
        try {
            CommandOption options = CommandOption.getInstance();
            String[] args = options.getCommandArgs();
            String category = options.getCategory();
            
            if (args.length < 2) {
                System.err.println("Missing arguments for add command");
                return 1;
            }

            String itemName = args[1];

            // If quantity is provided, use it; otherwise default to 1
            if (args.length >= 3) {
                int quantity = Integer.parseInt(args[2]);
                manager.add(itemName, quantity, category);
            } else {
                manager.add(itemName, 1, category);
            }

            manager.saveToFile();
            return 0;

        } catch (NumberFormatException e) {
            System.err.println("Invalid quantity format");
            return 1;
        }
    }
}