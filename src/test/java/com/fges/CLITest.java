/*package com.fges;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CLITest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @TempDir
    Path tempDir;
    private Path jsonFile;
    private Path csvFile;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // Create temp files for tests
        jsonFile = tempDir.resolve("Grocery.json");
        csvFile = tempDir.resolve("Grocery.csv");
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void exec_should_add_item_to_json_file() throws IOException {
        // Given
        String[] args = {"-f", "json", "add", "Apple", "5"};
        String currentDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        // When
        int result = CLI.exec(args);

        // Then
        System.setProperty("user.dir", currentDir);
        assertThat(result).isEqualTo(0);
        assertThat(Files.exists(jsonFile)).isTrue();
        String content = Files.readString(jsonFile);
        assertThat(content).contains("Apple");
        assertThat(content).contains("5");
    }

    @Test
    void exec_should_add_item_with_category() throws IOException {
        // Given
        String[] args = {"-f", "json", "-c", "Fruits", "add", "Banana", "3"};
        String currentDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        // When
        int result = CLI.exec(args);

        // Then
        System.setProperty("user.dir", currentDir);
        assertThat(result).isEqualTo(0);
        assertThat(Files.exists(jsonFile)).isTrue();
        String content = Files.readString(jsonFile);
        assertThat(content).contains("Fruits");
        assertThat(content).contains("Banana");
    }

    @Test
    void exec_should_list_items() throws IOException {
        // Given
        // First add an item
        String[] addArgs = {"-f", "json", "add", "Apple", "5"};
        String currentDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());
        CLI.exec(addArgs);

        // Clear output for clean test
        outContent.reset();

        // When listing
        String[] listArgs = {"-f", "json", "list"};
        int result = CLI.exec(listArgs);

        // Then
        System.setProperty("user.dir", currentDir);
        assertThat(result).isEqualTo(0);
        String output = outContent.toString();
        assertThat(output).contains("Apple");
        assertThat(output).contains("5");
    }

    @Test
    void exec_should_remove_item() throws IOException {
        // Given
        // First add items
        String[] addArgs = {"-f", "json", "add", "Apple", "5"};
        String[] addArgs2 = {"-f", "json", "add", "Banana", "3"};
        String currentDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());
        CLI.exec(addArgs);
        CLI.exec(addArgs2);

        // When removing
        String[] removeArgs = {"-f", "json", "remove", "Apple"};
        int result = CLI.exec(removeArgs);

        // Then
        String[] listArgs = {"-f", "json", "list"};
        outContent.reset();
        CLI.exec(listArgs);

        System.setProperty("user.dir", currentDir);
        assertThat(result).isEqualTo(0);
        String output = outContent.toString();
        assertThat(output).doesNotContain("Apple");
        assertThat(output).contains("Banana");
    }

    @Test
    void exec_should_work_with_csv_format() throws IOException {
        // Given
        String[] args = {"-f", "csv", "add", "Apple", "5"};
        String currentDir = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());

        // When
        int result = CLI.exec(args);

        // Then
        System.setProperty("user.dir", currentDir);
        assertThat(result).isEqualTo(0);
        assertThat(Files.exists(csvFile)).isTrue();
        String content = Files.readString(csvFile);
        assertThat(content).contains("Apple,5");
    }

    @Test
    void exec_should_return_error_for_invalid_format() {
        // Given
        String[] args = {"-f", "xml", "add", "Apple", "5"};

        // When
        int result = CLI.exec(args);

        // Then
        assertThat(result).isEqualTo(1);
        assertThat(errContent.toString()).contains("Unsupported format");
    }

    @Test
    void exec_should_return_error_for_missing_command() {
        // Given
        String[] args = {"-f", "json"};

        // When
        int result = CLI.exec(args);

        // Then
        assertThat(result).isEqualTo(1);
        assertThat(errContent.toString()).contains("Missing Command");
    }
}*/