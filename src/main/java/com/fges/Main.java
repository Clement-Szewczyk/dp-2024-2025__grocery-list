package com.fges;

import java.io.IOException;

/**
 * Main entry point class for the Grocery List application.
 * This class serves as the application's starting point and delegates execution
 * to the CLI class for command-line interface handling.
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
public class Main {
    /**
     * The main method that serves as the entry point for the JVM.
     * It delegates to the exec method and uses its return value as the exit code.
     *
     * @param args Command line arguments passed to the application
     * @throws IOException If an I/O error occurs during file operations
     */
    public static void main(String[] args) throws IOException {
        System.exit(exec(args));
    }

    /**
     * Executes the application by delegating to the CLI's exec method.
     * This method serves as a bridge between the main method and the CLI functionality.
     *
     * @param args Command line arguments to be processed
     * @return An integer representing the exit code: 0 for success, non-zero for failure
     * @throws IOException If an I/O error occurs during file operations
     */
    public static int exec(String[] args) throws IOException {
        return CLI.exec(args);
    }
}