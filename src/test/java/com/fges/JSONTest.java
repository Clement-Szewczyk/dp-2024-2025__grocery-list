package com.fges;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class JSONTest {

    @TempDir
    Path tempDir;

    private Path tempFile;
    private JSON json;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = tempDir.resolve("grocery.json");

        List <Item> groceryList = new ArrayList<>();
        groceryList.add(new Item("apple", 5, "Fruits"));
        groceryList.add(new Item("banana", 3, "Fruits"));

        json = new JSON();
        json.save(groceryList, tempFile.toString());
    }
    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void should_always_pass() {
        assertThat(true).isTrue();
    }
    @Test
    void should_load_existing_items_from_file() throws IOException {
        List<Item> loadedList = new ArrayList<>();
        json.load(loadedList, tempFile.toString());

        assertThat(loadedList).hasSize(2);
        assertThat(loadedList.get(0).getName()).isEqualTo("apple");
        assertThat(loadedList.get(1).getName()).isEqualTo("banana");
    }

    @Test
    void should_save_items_to_file() throws IOException {
        List<Item> newGroceryList = new ArrayList<>();
        newGroceryList.add(new Item("orange", 4, "Fruits"));

        json.save(newGroceryList, tempFile.toString());

        List<Item> loadedList = new ArrayList<>();
        json.load(loadedList, tempFile.toString());

        assertThat(loadedList).hasSize(1);
        assertThat(loadedList.get(0).getName()).isEqualTo("orange");
        assertThat(loadedList.get(0).getQuantity()).isEqualTo(4);
    }

    @Test
    void should_not_load_if_file_does_not_exist() throws IOException {
        Path nonExistentFile = tempDir.resolve("non_existent_file.json");

        List<Item> loadedList = new ArrayList<>();
        json.load(loadedList, nonExistentFile.toString());

        assertThat(loadedList).isEmpty();
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