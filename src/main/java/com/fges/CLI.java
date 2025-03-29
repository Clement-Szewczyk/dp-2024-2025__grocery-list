package com.fges;

import org.apache.commons.cli.*;
import java.io.IOException;


public class CLI {
    public static void main(String[] args) {
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
        boolean isCSV = fileName.endsWith(".csv");
        boolean isJSON = fileName.endsWith(".json");

        if (!isCSV && !isJSON) {
            System.err.println("Unsupported file format. Use .csv or .json");
            return 1;
        }

        String[] positionalArgs = cmd.getArgs();
        if (positionalArgs.length == 0) {
            System.err.println("Missing Command");
            return 1;
        }

        String command = positionalArgs[0];

        try {
            if (isCSV) {
                CSV csv = new CSV(fileName);
                return handleCSVCommand(csv, command, positionalArgs);
            } else {
                return Main.exec(args);
            }
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            return 1;
        }
    }

    private static int handleCSVCommand(CSV csv, String command, String[] args) {
        switch (command) {
            case "add":
                if (args.length < 3) {
                    System.err.println("Missing arguments for add command");
                    return 1;
                }
                try {
                    String item = args[1];
                    int quantity = Integer.parseInt(args[2]);
                    csv.add(item, quantity);
                    return 0;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid quantity format");
                    return 1;
                }

            case "list":
                for (String item : csv.list()) {
                    System.out.println(item);
                }
                return 0;

            case "remove":
                if (args.length < 2) {
                    System.err.println("Missing arguments for remove command");
                    return 1;
                }
                csv.remove(args[1]);
                return 0;

            default:
                System.err.println("Unknown command: " + command);
                return 1;
        }
    }
}
