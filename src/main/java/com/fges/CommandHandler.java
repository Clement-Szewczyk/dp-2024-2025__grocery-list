package com.fges;

import com.fges.command.Command;
import com.fges.command.CommandRegistry;


public class CommandHandler {
    private final GroceryListManager manager;
    private final CommandRegistry registry;

    public CommandHandler(GroceryListManager manager) {
        this.manager = manager;
        this.registry = new CommandRegistry();
    }

    public int handleCommand(String commandName, String[] args, String category) {
        if (!registry.exists(commandName)) {
            System.err.println("Unknown command: " + commandName);

            return 1;
        }

        Command command = registry.getCommand(commandName);
        return command.execute(args, manager, category);
    }
}