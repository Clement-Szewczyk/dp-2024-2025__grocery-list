package com.fges.command;

import com.fges.GroceryListManager;

/**
 * Implementation of the Add command for the grocery list application.
 * This class allows adding new items to the grocery list with optional quantity
 * and category specifications.
 * It implements the Command interface, providing execution logic for the add operation.
 */

public class Add implements Command {
    @Override
    public int execute(GroceryListManager manager) {
        try {
            // Get the command options singleton that contains parsed command arguments
            CommandOption options = CommandOption.getInstance();
            String[] args = options.getCommandArgs();
            String category = options.getCategory();

            // Validate that at least the item name is provided
            if (args.length < 2) {
                System.err.println("Missing arguments for add command");
                return 1;
            }

            // Extract the item name from arguments (second argument)
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
            // Handle the case where quantity is not a valid integer
            System.err.println("Invalid quantity format");
            return 1;
        }
    }
}