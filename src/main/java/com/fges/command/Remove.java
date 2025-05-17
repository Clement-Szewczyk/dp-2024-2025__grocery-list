package com.fges.command;

import com.fges.GroceryListManager;

public class Remove implements Command {
    @Override
    public int execute(GroceryListManager manager) {
        try {
            // Get the command options singleton that contains parsed command arguments
            CommandOption options = CommandOption.getInstance();
            String[] args = options.getCommandArgs();

            // Validate that at least the item name is provided
            if (args.length < 2) {
                System.err.println("Missing arguments for remove command");
                return 1;
            }

            // Extract the item name from arguments (second argument)
            String itemName = args[1];

            // Handle quantity - if provided, remove that specific quantity
            // Otherwise, remove the item entirely
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