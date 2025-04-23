package com.fges;

import org.apache.commons.cli.*;
import com.fges.dao.GroceryListDAOFactory;

public class CLI {
    public void main(String[] args) {
        System.exit(exec(args));
    }

    public static int exec(String[] args) {
        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();

        cliOptions.addOption(Option.builder("f")
                .longOpt("format")
                .hasArg()
                .desc("File format (json or csv). Default is json.")
                .build()
        );

        cliOptions.addOption(Option.builder("c")
                .longOpt("category")
                .hasArg()
                .desc("Item category. Default is 'default'.")
                .build()
        );

        CommandLine cmd;
        try {
            cmd = parser.parse(cliOptions, args);
            String format = cmd.getOptionValue("format", "json").toLowerCase();
            if (!format.equals("json") && !format.equals("csv")) {
                System.err.println("Unsupported format: " + format);
                return 1;
            }

            String fileName = "Grocery." + format;
            String category = cmd.getOptionValue("category", "default");

            // Create the DAO
            var dao = GroceryListDAOFactory.createDAO(fileName, format);

            // Create and initialize the manager
            var manager = new GroceryListManager(dao);
            manager.initialize();

            // Create command handler
            var commandHandler = new CommandHandler(manager);

            String[] positionalArgs = cmd.getArgs();
            if (positionalArgs.length == 0) {
                System.err.println("Missing Command");
                return 1;
            }

            String command = positionalArgs[0];
            // Use the command handler instead of the manager
            return commandHandler.handleCommand(command, positionalArgs, category);

        } catch (ParseException ex) {
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }
    }
}