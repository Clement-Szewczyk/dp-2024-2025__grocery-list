package com.fges;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fges.dao.GroceryListDAO;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.fges.command.CommandOption;
import com.fges.dao.GroceryListDAOFactory;

/**
 * Command Line Interface handler class for the grocery list application.
 * This class processes command line arguments and delegates operations to appropriate handlers.
 */

public class CLI {
    public void main(String[] args) {
        System.exit(exec(args));
    }

    /**
     * Executes the command line operations based on provided arguments.
     * Parses the arguments, validates them, initializes necessary components,
     and delegates the command execution to the appropriate handler.
     */

    public static int exec(String[] args) {
        // Reset options for a clean state (important for testing)
        CommandOption.reset();
        CommandOption options = CommandOption.getInstance();

        try {
            // Parse and validate command line arguments
            parseCommandLineArguments(args, options);

            // Create and run the application components
            return createAndRunApplication();
        } catch (ParseException ex) {
            // Handle parsing errors
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        } catch (IllegalArgumentException ex) {
            // Handle validation errors
            System.err.println(ex.getMessage());
            return 1;
        }
    }


    private static void parseCommandLineArguments(String[] args, CommandOption options)
            throws ParseException, IllegalArgumentException {
        // Initialize command line options
        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();

        cliOptions.addOption("s", "source", true, "File containing the grocery list");

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
                .desc("GroceryItem category. Default is 'default'.")
                .build()
        );

        // Parse the command line arguments
        CommandLine cmd = parser.parse(cliOptions, args);

        // Get the format option value, defaulting to "json" if not specified
        String format = cmd.getOptionValue("format", "json").toLowerCase();
        options.setFormat(format);

        // Validate the format value
        if (!format.equals("json") && !format.equals("csv")) {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }

        // Set the filename
        String fileName = cmd.getOptionValue("source");
        options.setFileName(fileName);

        // Get the category option value, defaulting to "default" if not specified
        String category = cmd.getOptionValue("category", "default");
        options.setCategory(category);

        // Get positional arguments (non-option arguments)
        String[] positionalArgs = cmd.getArgs();
        options.setCommandArgs(positionalArgs);

        // Check if a command was provided
        if (positionalArgs.length == 0) {
            throw new IllegalArgumentException("Missing Command");
        }

        // Extract the command (first positional argument)
        String command = positionalArgs[0];
        options.setCommand(command);
    }

    /**
     * Creates application components and runs the command using the stored options.
     * @return The result code of the command execution
     */
    private static int createAndRunApplication() {
        CommandOption options = CommandOption.getInstance();

        // Create the Data Access Object (DAO) for the specified format
        GroceryListDAO dao;

        if (options.getCommand().equals("info")){
            var commandHandler = new CommandHandler(null); // on passe null ou un manager vide
            return commandHandler.handleCommand();
        } else {
            dao = GroceryListDAOFactory.createDAO(options.getFileName(), options.getFormat());
        }

        // Create and initialize the GroceryListManager with the DAO
        var manager = new GroceryListManager(dao);
        manager.initialize();

        // Create a CommandHandler to process the commands
        var commandHandler = new CommandHandler(manager);

        // Delegate command handling to the CommandHandler
        return commandHandler.handleCommand();
    }
}