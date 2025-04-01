package com.fges;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CSVTest {
    @Test
    void should_allways_pass() {
        assertThat(true).isTrue();
    }

    /*@TempDir
    Path tempDir;

    private Path tempFile;
    private CSV csv;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = tempDir.resolve("grocery.csv");
        // Initialize with some data
        Files.write(tempFile, Arrays.asList("apple,5", "banana,3"));
        csv = new CSV(tempFile.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void should_load_existing_items_from_file() {
        List<String> items = csv.list();

        assertThat(items).hasSize(2);
        assertThat(items).contains("apple,5", "banana,3");
    }

    @Test
    void should_add_new_item() {
        csv.add("orange", 2);

        List<String> items = csv.list();
        assertThat(items).hasSize(3);
        assertThat(items).contains("apple,5", "banana,3", "orange,2");
    }

    @Test
    void should_increase_quantity_of_existing_item() {
        csv.add("apple", 3);

        List<String> items = csv.list();
        assertThat(items).hasSize(2);
        assertThat(items).contains("apple,8", "banana,3");
    }

    @Test
    void should_remove_item_completely() {
        csv.remove("apple");

        List<String> items = csv.list();
        assertThat(items).hasSize(1);
        assertThat(items).contains("banana,3");
    }

    @Test
    void should_reduce_quantity_of_item() {
        csv.remove("apple", 2);

        List<String> items = csv.list();
        assertThat(items).hasSize(2);
        assertThat(items).contains("apple,3", "banana,3");
    }

    @Test
    void should_remove_item_when_quantity_equals_or_exceeds_current() {
        csv.remove("apple", 5);

        List<String> items = csv.list();
        assertThat(items).hasSize(1);
        assertThat(items).contains("banana,3");

        csv.remove("banana", 10);
        items = csv.list();
        assertThat(items).isEmpty();
    }

    @Test
    void should_persist_changes_to_file() throws IOException {
        csv.add("orange", 2);
        csv.remove("banana");

        // Create a new CSV instance to read from the file
        CSV newCsv = new CSV(tempFile.toString());
        List<String> items = newCsv.list();

        assertThat(items).hasSize(2);
        assertThat(items).contains("apple,5", "orange,2");
    }

    @Test
    void should_create_new_file_if_not_exists() throws IOException {
        Path newFile = tempDir.resolve("new_grocery.csv");
        CSV newCsv = new CSV(newFile.toString());

        newCsv.add("pear", 4);

        assertThat(Files.exists(newFile)).isTrue();
        List<String> fileContents = Files.readAllLines(newFile);
        assertThat(fileContents).containsExactly("pear,4");
    }*/
}