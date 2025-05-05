package com.fges;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.fges.command.CommandOption;

class CommandHandlerTest {

    private GroceryListManager mockManager;
    private CommandHandler handler;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        mockManager = mock(GroceryListManager.class);
        handler = new CommandHandler(mockManager);

        // Redirect stdout and stderr for testing output
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        
        // Reset CommandOption before each test to ensure a clean state
        CommandOption.reset();
    }

    @Test
    void handleCommand_list_should_call_manager_list() {
        // Given
        String[] args = {"list"};
        CommandOption options = CommandOption.getInstance();
        options.setCommand("list");
        options.setCommandArgs(args);
        options.setCategory("default");

        // When
        int result = handler.handleCommand();

        // Then
        verify(mockManager).list();
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_add_should_call_manager_add_with_quantity() {
        // Given
        String[] args = {"add", "Apple", "5"};
        CommandOption options = CommandOption.getInstance();
        options.setCommand("add");
        options.setCommandArgs(args);
        options.setCategory("Fruits");

        // When
        int result = handler.handleCommand();

        // Then
        verify(mockManager).add("Apple", 5, "Fruits");
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_add_should_use_default_quantity_when_not_provided() {
        // Given
        String[] args = {"add", "Apple"};
        CommandOption options = CommandOption.getInstance();
        options.setCommand("add");
        options.setCommandArgs(args);
        options.setCategory("Fruits");

        // When
        int result = handler.handleCommand();

        // Then
        verify(mockManager).add("Apple", 1, "Fruits");
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_remove_should_call_manager_remove_with_quantity() {
        // Given
        String[] args = {"remove", "Apple", "3"};
        CommandOption options = CommandOption.getInstance();
        options.setCommand("remove");
        options.setCommandArgs(args);
        options.setCategory("default");

        // When
        int result = handler.handleCommand();

        // Then
        verify(mockManager).remove("Apple", 3);
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_remove_should_call_manager_remove_without_quantity() {
        // Given
        String[] args = {"remove", "Apple"};
        CommandOption options = CommandOption.getInstance();
        options.setCommand("remove");
        options.setCommandArgs(args);
        options.setCategory("default");

        // When
        int result = handler.handleCommand();

        // Then
        verify(mockManager).remove("Apple");
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_should_return_error_code_for_unknown_command() {
        // Given
        String[] args = {"unknown", "Apple"};
        CommandOption options = CommandOption.getInstance();
        options.setCommand("unknown");
        options.setCommandArgs(args);
        options.setCategory("default");

        // When
        int result = handler.handleCommand();

        // Then
        assertThat(result).isEqualTo(1);
        assertThat(errContent.toString()).contains("Unknown command");
    }

    @Test
    void handleCommand_should_return_error_code_for_missing_arguments() {
        // Given
        String[] args = {"add"};
        CommandOption options = CommandOption.getInstance();
        options.setCommand("add");
        options.setCommandArgs(args);
        options.setCategory("default");

        // When
        int result = handler.handleCommand();

        // Then
        assertThat(result).isEqualTo(1);
        assertThat(errContent.toString()).contains("Missing arguments");
    }

    @Test
    void handleCommand_should_return_error_code_for_invalid_quantity() {
        // Given
        String[] args = {"add", "Apple", "notANumber"};
        CommandOption options = CommandOption.getInstance();
        options.setCommand("add");
        options.setCommandArgs(args);
        options.setCategory("default");

        // When
        int result = handler.handleCommand();

        // Then
        assertThat(result).isEqualTo(1);
        assertThat(errContent.toString()).contains("Invalid quantity");
    }
}