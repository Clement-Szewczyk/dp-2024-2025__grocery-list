package com.fges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
    }

    @Test
    void handleCommand_list_should_call_manager_list() {
        // Given
        String[] args = {"list"};

        // When
        int result = handler.handleCommand("list", args, "default");

        // Then
        verify(mockManager).list();
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_add_should_call_manager_add_with_quantity() {
        // Given
        String[] args = {"add", "Apple", "5"};

        // When
        int result = handler.handleCommand("add", args, "Fruits");

        // Then
        verify(mockManager).add("Apple", 5, "Fruits");
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_add_should_use_default_quantity_when_not_provided() {
        // Given
        String[] args = {"add", "Apple"};

        // When
        int result = handler.handleCommand("add", args, "Fruits");

        // Then
        verify(mockManager).add("Apple", 1, "Fruits");
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_remove_should_call_manager_remove_with_quantity() {
        // Given
        String[] args = {"remove", "Apple", "3"};

        // When
        int result = handler.handleCommand("remove", args, "default");

        // Then
        verify(mockManager).remove("Apple", 3);
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_remove_should_call_manager_remove_without_quantity() {
        // Given
        String[] args = {"remove", "Apple"};

        // When
        int result = handler.handleCommand("remove", args, "default");

        // Then
        verify(mockManager).remove("Apple");
        verify(mockManager).saveToFile();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void handleCommand_should_return_error_code_for_unknown_command() {
        // Given
        String[] args = {"unknown", "Apple"};

        // When
        int result = handler.handleCommand("unknown", args, "default");

        // Then
        assertThat(result).isEqualTo(1);
        assertThat(errContent.toString()).contains("Unknown command");
    }

    @Test
    void handleCommand_should_return_error_code_for_missing_arguments() {
        // Given
        String[] args = {"add"};

        // When
        int result = handler.handleCommand("add", args, "default");

        // Then
        assertThat(result).isEqualTo(1);
        assertThat(errContent.toString()).contains("Missing arguments");
    }

    @Test
    void handleCommand_should_return_error_code_for_invalid_quantity() {
        // Given
        String[] args = {"add", "Apple", "notANumber"};

        // When
        int result = handler.handleCommand("add", args, "default");

        // Then
        assertThat(result).isEqualTo(1);
        assertThat(errContent.toString()).contains("Invalid quantity");
    }
}