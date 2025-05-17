package com.fges.command;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    /**
     * Map that stores all available commands, with command names as keys
     * and Command implementations as values.
     */
    private final Map<String, Command> commands = new HashMap<>();
    /**
     * Constructor that initializes the command registry with all supported commands.
     * Each command is registered with its corresponding command name as the key.
     */

    public CommandRegistry() {

        commands.put("add", new Add());
        commands.put("remove", new Remove());
        commands.put("list", new List());
        commands.put("web", new Web());
        commands.put("info", new Info());
    }
    public Command getCommand(String commandName) {
        return commands.get(commandName.toLowerCase());
    }


    /**
     * Checks if a command with the given name exists in the registry.
     */
    public boolean exists(String commandName) {
        return commands.containsKey(commandName.toLowerCase());
    }
}