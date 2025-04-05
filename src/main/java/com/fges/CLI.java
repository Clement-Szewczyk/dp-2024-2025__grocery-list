package com.fges;

import org.apache.commons.cli.*;

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

        CommandLine cmd;
        try {
            cmd = parser.parse(cliOptions, args);
            String format = cmd.getOptionValue("format", "json").toLowerCase();
            if (!format.equals("json") && !format.equals("csv")) {
                System.err.println("Unsupported format: " + format);
                return 1;
            }
            String fileName = "liste." + format;

        } catch (ParseException ex) {
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }
        String format = cmd.getOptionValue("format", "json").toLowerCase();
        String fileName = "liste." + format;

        GroceryListManager groceryManager = new GroceryListManager(fileName, format);

        String[] positionalArgs = cmd.getArgs();
        if (positionalArgs.length == 0) {
            System.err.println("Missing Command");
            return 1;
        }

        String command = positionalArgs[0];
        return groceryManager.handleCommand(command, positionalArgs);
    }
}