package com.fges;

/**
 * CommandHandler class processes and executes commands for the grocery list application.
 * It interprets command line arguments and delegates the appropriate operations to the GroceryListManager.
 * This class supports commands like list, add, and remove for managing grocery items.
 */

public class CommandHandler {
    private final GroceryListManager manager;

    public CommandHandler(GroceryListManager manager) {
        this.manager = manager;
    }
    /**
     * Processes and executes a command based on the provided arguments.
     * Supported commands:
      - list: Lists all grocery items
      - add: Adds an item with specified name, quantity, and category
      - remove: Removes an item or reduces its quantity
     */
    public int handleCommand(String command, String[] args, String category) {
        try {
            // Handle the "list" command
            if (command.equals("list")) {
                manager.list();
            } else {
                // For other commands, check if we have enough arguments
                if (args.length < 2) {
                    System.err.println("Missing arguments for " + command + " command");
                    return 1;
                }

                // Extract item name from arguments
                String itemName = args[1];

                // Process commands using switch expression
                switch (command) {
                    case "add" -> {
                        // If quantity is provided, use it; otherwise default to 1
                        if (args.length >= 3) {
                            int quantity = Integer.parseInt(args[2]);
                            manager.add(itemName, quantity, category);
                        } else {
                            manager.add(itemName, 1, category);
                        }
                    }
                    case "remove" -> {
                        // If quantity is provided, remove that amount; otherwise remove the item entirely
                        if (args.length >= 3) {
                            int quantity = Integer.parseInt(args[2]);
                            manager.remove(itemName, quantity);
                        } else {
                            manager.remove(itemName);
                        }
                    }
                    default -> {
                        // Handle unknown commands
                        System.err.println("Unknown command: " + command);
                        return 1;
                    }
                }
            }
            // Save changes to file after successful command execution
            manager.saveToFile();
            return 0;

        } catch (NumberFormatException e) {
            // Handle parsing errors for quantity values
            System.err.println("Invalid quantity format");
            return 1;
        }
    }
}