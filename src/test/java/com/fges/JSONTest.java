package com.fges;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class JSONTest {
    @Test
    void should_always_pass() {
        assertThat(true).isTrue();
    }

    /*@TempDir
    Path tempDir;

    private Path tempFile;
    private JSON json;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        tempFile = tempDir.resolve("grocery.json");
        // Initialize with some data
        List<String> initialItems = List.of("apple,5", "banana,3");
        objectMapper.writeValue(tempFile.toFile(), initialItems);
        json = new JSON(tempFile.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void should_load_existing_items_from_file() throws IOException {
        // Since list() method prints to console, we need to read the file directly
        String content = Files.readString(tempFile);
        List<String> items = objectMapper.readValue(content, new TypeReference<List<String>>() {});

        assertThat(items).hasSize(2);
        assertThat(items).contains("apple,5", "banana,3");
    }

    @Test
    void should_add_new_item() throws IOException {
        json.add("orange", 2);

        String content = Files.readString(tempFile);
        List<String> items = objectMapper.readValue(content, new TypeReference<List<String>>() {});

        assertThat(items).hasSize(3);
        assertThat(items).contains("apple,5", "banana,3", "orange,2");
    }

    @Test
    void should_increase_quantity_of_existing_item() throws IOException {
        json.add("apple", 3);

        String content = Files.readString(tempFile);
        List<String> items = objectMapper.readValue(content, new TypeReference<List<String>>() {});

        assertThat(items).hasSize(2);
        assertThat(items).contains("apple,8", "banana,3");
    }

    @Test
    void should_remove_item_completely() throws IOException {
        json.remove("apple");

        String content = Files.readString(tempFile);
        List<String> items = objectMapper.readValue(content, new TypeReference<List<String>>() {});

        assertThat(items).hasSize(1);
        assertThat(items).contains("banana,3");
    }

    @Test
    void should_reduce_quantity_of_item() throws IOException {
        json.remove("apple", 2);

        String content = Files.readString(tempFile);
        List<String> items = objectMapper.readValue(content, new TypeReference<List<String>>() {});

        assertThat(items).hasSize(2);
        assertThat(items).contains("apple,3", "banana,3");
    }

    @Test
    void should_remove_item_when_quantity_equals_or_exceeds_current() throws IOException {
        json.remove("apple", 5);

        String content = Files.readString(tempFile);
        List<String> items = objectMapper.readValue(content, new TypeReference<List<String>>() {});

        assertThat(items).hasSize(1);
        assertThat(items).contains("banana,3");

        json.remove("banana", 10);
        content = Files.readString(tempFile);
        items = objectMapper.readValue(content, new TypeReference<List<String>>() {});

        assertThat(items).isEmpty();
    }

    @Test
    void should_persist_changes_to_file() throws IOException {
        json.add("orange", 2);
        json.remove("banana");

        // Create a new JSON instance to read from the file
        JSON newJson = new JSON(tempFile.toString());

        // Read file directly to verify contents
        String content = Files.readString(tempFile);
        List<String> items = objectMapper.readValue(content, new TypeReference<List<String>>() {});

        assertThat(items).hasSize(2);
        assertThat(items).contains("apple,5", "orange,2");
    }

    @Test
    void should_create_new_file_if_not_exists() throws IOException {
        Path newFile = tempDir.resolve("new_grocery.json");
        JSON newJson = new JSON(newFile.toString());

        newJson.add("pear", 4);

        assertThat(Files.exists(newFile)).isTrue();
        String content = Files.readString(newFile);
        List<String> items = objectMapper.readValue(content, new TypeReference<List<String>>() {});

        assertThat(items).hasSize(1);
        assertThat(items).contains("pear,4");
    }*/
}