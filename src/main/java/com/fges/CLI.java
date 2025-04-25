package com.fges;

import org.apache.commons.cli.*;
import com.fges.dao.GroceryListDAOFactory;

/**
 * Command Line Interface handler class for the grocery list application.
 * This class processes command line arguments and delegates operations to appropriate handlers.
 * It uses Apache Commons CLI for command line parsing and provides options for file format and item category.
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */

public class CLI {
    /**
     * Main entry point of the application.
     * Calls the exec method and uses its return value as the exit code.
     *
     * @param args Command line arguments passed to the application
     */
    public void main(String[] args) {
        System.exit(exec(args));
    }

    /**
     * Executes the command line operations based on provided arguments.
     * Parses the arguments, validates them, initializes necessary components,
     * and delegates the command execution to the appropriate handler.
     *
     * @param args Command line arguments to process
     * @return An integer representing the exit code: 0 for success, non-zero for failure
     */

    public static int exec(String[] args) {
        // Initialize command line options
        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();

        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");

        // Define the format option (-f, --format)
        cliOptions.addOption(Option.builder("f")
                .longOpt("format")
                .hasArg()
                .desc("File format (json or csv). Default is json.")
                .build()
        );

        // Define the category option (-c, --category)
        cliOptions.addOption(Option.builder("c")
                .longOpt("category")
                .hasArg()
                .desc("Item category. Default is 'default'.")
                .build()
        );

        CommandLine cmd;
        try {
            // Parse the command line arguments
            cmd = parser.parse(cliOptions, args);

            // Get the format option value, defaulting to "json" if not specified
            String format = cmd.getOptionValue("format", "json").toLowerCase();

            // Validate the format value
            if (!format.equals("json") && !format.equals("csv")) {
                System.err.println("Unsupported format: " + format);
                return 1;
            }

            // Construct the filename based on the format
            String fileName = cmd.getOptionValue("source");

            // Get the category option value, defaulting to "default" if not specified
            String category = cmd.getOptionValue("category", "default");

            // Create the Data Access Object (DAO) for the specified format
            var dao = GroceryListDAOFactory.createDAO(fileName, format);

            // Create and initialize the GroceryListManager with the DAO
            var manager = new GroceryListManager(dao);
            manager.initialize();

            // Create a CommandHandler to process the commands
            var commandHandler = new CommandHandler(manager);

            // Get positional arguments (non-option arguments)
            String[] positionalArgs = cmd.getArgs();

            // Check if a command was provided
            if (positionalArgs.length == 0) {
                System.err.println("Missing Command");
                return 1;
            }

            // Extract the command (first positional argument)
            String command = positionalArgs[0];

            // Delegate command handling to the CommandHandler
            return commandHandler.handleCommand(command, positionalArgs, category);

        } catch (ParseException ex) {
            // Handle parsing errors
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }
    }
}