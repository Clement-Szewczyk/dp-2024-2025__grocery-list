package com.fges;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        // Manage the case where the user wants to display the system information
        if (args.length > 0 && args[0].equalsIgnoreCase("info")) {
            // Display system information and date
            String osName = System.getProperty("os.name");
            String javaVersion = System.getProperty("java.version");
            LocalDate today = LocalDate.now();
            String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println("Today's date : " + todayDate);
            System.out.println("Operating System : " + osName);
            System.out.println("Java version : " + javaVersion);
            return 0;
        }

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
                .desc("GroceryItem category. Default is 'default'.")
                .build()
        );

        CommandLine cmd;
        try {
            // Parse the command line arguments
            cmd = parser.parse(cliOptions, args);

            // Get the format option value, defaulting to "json" if not specified
            String format = cmd.getOptionValue("format", "json").toLowerCase();
            options.setFormat(format);

            // Validate the format value
            if (!format.equals("json") && !format.equals("csv")) {
                System.err.println("Unsupported format: " + format);
                return 1;
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
                System.err.println("Missing Command");
                return 1;
            }

            // Extract the command (first positional argument)
            String command = positionalArgs[0];
            options.setCommand(command);

            // Create and run the application components
            return createAndRunApplication();

        } catch (ParseException ex) {
            // Handle parsing errors
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }
    }

    /**
     * Creates application components and runs the command using the stored options.
     * @return The result code of the command execution
     */
    private static int createAndRunApplication() {
        CommandOption options = CommandOption.getInstance();

        // Create the Data Access Object (DAO) for the specified format
        var dao = GroceryListDAOFactory.createDAO(options.getFileName(), options.getFormat());

        // Create and initialize the GroceryListManager with the DAO
        var manager = new GroceryListManager(dao);
        manager.initialize();

        // Create a CommandHandler to process the commands
        var commandHandler = new CommandHandler(manager);

        // Delegate command handling to the CommandHandler
        return commandHandler.handleCommand();
    }
}