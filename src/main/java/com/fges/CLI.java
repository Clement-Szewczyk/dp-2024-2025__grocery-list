package com.fges;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
        
        // Add category option
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

        } catch (ParseException ex) {
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }
        String format = cmd.getOptionValue("format", "json").toLowerCase();
        String fileName = "Grocery." + format;
        
        // Get category from command line or use default
        String category = cmd.getOptionValue("category", "default");

        GroceryListManager groceryManager = new GroceryListManager(fileName, format);
        groceryManager.initialize();

        String[] positionalArgs = cmd.getArgs();
        if (positionalArgs.length == 0) {
            System.err.println("Missing Command");
            return 1;
        }

        String command = positionalArgs[0];
        // Pass category to handleCommand
        return groceryManager.handleCommand(command, positionalArgs, category);
    }
}
