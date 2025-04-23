package com.fges;

public class CommandHandler {
    private final GroceryListManager manager;

    public CommandHandler(GroceryListManager manager) {
        this.manager = manager;
    }

    public int handleCommand(String command, String[] args, String category) {
        try {
            if (command.equals("list")) {
                manager.list();
            } else {
                if (args.length < 2) {
                    System.err.println("Missing arguments for " + command + " command");
                    return 1;
                }
                String itemName = args[1];
                switch (command) {
                    case "add" -> {
                        if (args.length >= 3) {
                            int quantity = Integer.parseInt(args[2]);
                            manager.add(itemName, quantity, category);
                        } else {
                            manager.add(itemName, 1, category);
                        }
                    }
                    case "remove" -> {
                        if (args.length >= 3) {
                            int quantity = Integer.parseInt(args[2]);
                            manager.remove(itemName, quantity);
                        } else {
                            manager.remove(itemName);
                        }
                    }
                    default -> {
                        System.err.println("Unknown command: " + command);
                        return 1;
                    }
                }
            }
            manager.saveToFile();
            return 0;

        } catch (NumberFormatException e) {
            System.err.println("Invalid quantity format");
            return 1;
        }
    }
}