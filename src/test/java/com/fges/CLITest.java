package com.fges;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the CLI class.
 * This test suite verifies the behavior of the CLI application for managing grocery lists.
 * It covers both valid and invalid command-line inputs and ensures the program responds as expected.
 *
 * These tests make use of the AssertJ and JUnit Jupiter frameworks for assertions and test definitions.
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
class CLITest {

    /**
     * Basic test to ensure the test suite setup is functional.
     * This test always passes.
     */
    @Test
    void should_always_pass() {
        assertThat(true).isTrue();
    }

    /**
     * Test for successful execution when valid command-line options are provided.
     * Verifies that the CLI accepts a correct format and a proper "add" command.
     */
    @Test
    void should_pass_with_valid_command_options() {
        String[] args = {"-f", "csv", "-c", "Fruits", "add", "Pomme", "2"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(0);
    }

    /**
     * Test for failure when an unsupported file format is specified.
     * The CLI should reject the "txt" format and return an error code.
     */
    @Test
    void should_not_pass_with_invalid_format() {
        String[] args = {"-f", "txt", "-c", "Fruits", "add", "Pomme", "2"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(1);
    }

    /**
     * Test for failure when the command is missing from the arguments.
     * The CLI should detect the missing operation and return an error code.
     */
    @Test
    void should_not_pass_with_missing_command() {
        String[] args = {"-f", "csv", "-c", "Fruits"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(1);
    }

    /**
     * Test for failure when an unknown command is specified.
     * The CLI should handle unrecognized commands gracefully by returning an error.
     */
    @Test
    void should_not_pass_with_unknown_command() {
        String[] args = {"-f", "csv", "-c", "Fruits", "unknown"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(1);
    }

    /**
     * Test for failure when the quantity argument is not a valid number.
     * The CLI should detect the invalid input type and return an error code.
     */
    @Test
    void should_not_pass_with_invalid_quantity() {
        String[] args = {"-f", "csv", "-c", "Fruits", "add", "Pomme", "not-a-number"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(1);
    }

    /**
     * Test for successful execution with default options (no format or category explicitly set).
     * Verifies that the CLI defaults are correctly applied and the command "list" is valid.
     */
    @Test
    void should_pass_with_default_options() {
        String[] args = {"list"};
        int result = CLI.exec(args);
        assertThat(result).isEqualTo(0);
    }
}
