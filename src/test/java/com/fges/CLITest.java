package com.fges;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @TempDir
    Path tempDir;

    private String csvFilePath;
    private String jsonFilePath;

    @BeforeEach
    void setUp() throws IOException {
        // Rediriger stdout et stderr pour les capturer pendant les tests
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // Créer des fichiers temporaires pour les tests
        csvFilePath = tempDir.resolve("test.csv").toString();
        jsonFilePath = tempDir.resolve("test.json").toString();

        // Créer des fichiers vides
        Files.createFile(Path.of(csvFilePath));
        Files.createFile(Path.of(jsonFilePath));
    }

    @Test
    void testMissingSourceOption() {
        String[] args = {"list"};
        int result = CLI.exec(args);

        assertEquals(1, result);
        assertTrue(errContent.toString().contains("Fail to parse arguments"));
    }

    @Test
    void testUnsupportedFileFormat() {
        String unsupportedFile = tempDir.resolve("test.txt").toString();
        try {
            Files.createFile(Path.of(unsupportedFile));
        } catch (IOException e) {
            fail("Failed to create test file");
        }

        String[] args = {"-s", unsupportedFile, "list"};
        int result = CLI.exec(args);

        assertEquals(1, result);
        assertTrue(errContent.toString().contains("Unsupported file format"));
    }

    @Test
    void testMissingCommand() {
        String[] args = {"-s", csvFilePath};
        int result = CLI.exec(args);

        assertEquals(1, result);
        assertTrue(errContent.toString().contains("Missing Command"));
    }

    @Test
    void testUnknownCommand() {
        String[] args = {"-s", csvFilePath, "unknown"};
        int result = CLI.exec(args);

        assertEquals(1, result);
        assertTrue(errContent.toString().contains("Unknown command"));
    }

    @Test
    void testAddCommandMissingArgs() {
        String[] args = {"-s", csvFilePath, "add"};
        int result = CLI.exec(args);

        assertEquals(1, result);
        assertTrue(errContent.toString().contains("Missing arguments for add command"));
    }

    @Test
    void testAddCommandInvalidQuantity() {
        String[] args = {"-s", csvFilePath, "add", "apple", "not-a-number"};
        int result = CLI.exec(args);

        assertEquals(1, result);
        assertTrue(errContent.toString().contains("Invalid quantity format"));
    }

    @Test
    void testRemoveCommandMissingArgs() {
        String[] args = {"-s", csvFilePath, "remove"};
        int result = CLI.exec(args);

        assertEquals(1, result);
        assertTrue(errContent.toString().contains("Missing arguments for remove command"));
    }

    @Test
    void testRemoveCommandInvalidQuantity() {
        String[] args = {"-s", csvFilePath, "remove", "apple", "not-a-number"};
        int result = CLI.exec(args);

        assertEquals(1, result);
        assertTrue(errContent.toString().contains("Invalid quantity format"));
    }

    @Test
    void testCSVProcessing() {

        String[] args = {"-s", csvFilePath, "list"};
        int result = CLI.exec(args);
        assertEquals(0, result);
    }

    @Test
    void testJSONProcessing() {
        String[] args = {"-s", jsonFilePath, "list"};
        int result = CLI.exec(args);
        assertEquals(0, result);
    }

    @Test
    void testFileNotFound() {
        String nonExistentFile = tempDir.resolve("nonexistent.csv").toString();
        String[] args = {"-s", nonExistentFile, "list"};
        int result = CLI.exec(args);

        assertEquals(0, result);

    }


    // Nettoyage après chaque test
    @org.junit.jupiter.api.AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}