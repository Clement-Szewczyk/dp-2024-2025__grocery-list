package com.fges;

import com.fges.command.Command;
import com.fges.command.CommandOption;
import com.fges.command.CommandRegistry;

public class CommandHandler {
    private final GroceryListManager manager;
    private final CommandRegistry registry;


 //Constructor that initializes the command handler with a grocery list manager.
    public CommandHandler(GroceryListManager manager) {
        this.manager = manager;
        this.registry = new CommandRegistry();
    }


     //Handles the command execution by retrieving command parameters from CommandOption

    public int handleCommand() {
        CommandOption options = CommandOption.getInstance();
        String commandName = options.getCommand();

        // Check if the command exists in the registry
        if (!registry.exists(commandName)) {
            System.err.println("Unknown command: " + commandName);
            return 1;
        }

        // Retrieve and execute the command
        Command command = registry.getCommand(commandName);
        return command.execute(manager);
    }
}