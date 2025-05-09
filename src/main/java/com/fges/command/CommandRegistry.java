package com.fges.command;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();


    public CommandRegistry() {
        commands.put("add", new Add());
        commands.put("remove", new Remove());
        commands.put("list", new List());
    }
    public Command getCommand(String commandName) {
        return commands.get(commandName.toLowerCase());
    }

    public boolean exists(String commandName) {
        return commands.containsKey(commandName.toLowerCase());
    }
}