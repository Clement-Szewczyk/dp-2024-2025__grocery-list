package com.fges;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CLI {
    public void main(String[] args) {
        System.exit(exec(args));
    }

    public static int exec(String[] args) {
        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();

        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");

        CommandLine cmd;
        try {
            cmd = parser.parse(cliOptions, args);
        } catch (ParseException ex) {
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }

        String fileName = cmd.getOptionValue("s");

        GroceryListManager groceryManager = new GroceryListManager(fileName);

        String[] positionalArgs = cmd.getArgs();
        if (positionalArgs.length == 0) {
            System.err.println("Missing Command");
            return 1;
        }

        String command = positionalArgs[0];
        return groceryManager.handleCommand(command, positionalArgs);
    }
}