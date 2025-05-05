package com.fges;

import com.fges.command.Command;
import com.fges.command.CommandOption;
import com.fges.command.CommandRegistry;

public class CommandHandler {
    private final GroceryListManager manager;
    private final CommandRegistry registry;

    public CommandHandler(GroceryListManager manager) {
        this.manager = manager;
        this.registry = new CommandRegistry();
    }

    /**
     * Handles the command execution by retrieving command parameters from CommandOption
     * @return The result code of the command execution
     */
    public int handleCommand() {
        CommandOption options = CommandOption.getInstance();
        String commandName = options.getCommand();
        
        if (!registry.exists(commandName)) {
            System.err.println("Unknown command: " + commandName);
            return 1;
        }

        Command command = registry.getCommand(commandName);
        return command.execute(manager);
    }
}