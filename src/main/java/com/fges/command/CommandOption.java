package com.fges.command;

/**
 * Singleton class that stores application-wide command options.
 * This class provides a central repository for values parsed from the CLI
 * so they can be accessed throughout the application without parameter passing.
 */
public class CommandOption {
    private static CommandOption instance;
    
    private String fileName;
    private String format;
    private String category;
    private String[] commandArgs;
    private String command;
    
    // Private constructor to enforce singleton pattern
    private CommandOption() {
        // Default values
        this.format = "json";
        this.category = "default";
    }
    
    /**
     * Gets the singleton instance of the command options.
     */
    public static synchronized CommandOption getInstance() {
        if (instance == null) {
            instance = new CommandOption();
        }
        return instance;
    }
    
    /**
     * Resets the options for testing purposes.
     */
    public static void reset() {
        instance = null;
    }
    
    // Getters and setters
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFormat() {
        return format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String[] getCommandArgs() {
        return commandArgs;
    }
    
    public void setCommandArgs(String[] commandArgs) {
        this.commandArgs = commandArgs;
    }
    
    public String getCommand() {
        return command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
}