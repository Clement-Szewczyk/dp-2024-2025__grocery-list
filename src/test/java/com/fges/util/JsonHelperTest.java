package com.fges.util;

import com.fges.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JsonHelperTest {

    @TempDir
    Path tempDir;

    @Test
    void saveToFile_should_save_multiple_items_in_array_format() throws IOException {
        // Given - multiple items
        List<Item> groceryList = Arrays.asList(
                new Item("Apple", 5, "Fruits"),
                new Item("Banana", 3, "Fruits"),
                new Item("Milk", 1, "Dairy")
        );

        File tempFile = tempDir.resolve("test_multiple.json").toFile();

        // When
        JsonHelper.saveToFile(groceryList, tempFile.getAbsolutePath());

        // Then
        String content = Files.readString(tempFile.toPath());
        assertThat(content).startsWith("["); // Should be an array
        assertThat(content).endsWith("]");
        assertThat(content).contains("\"name\":\"Apple\"");
        assertThat(content).contains("\"category\":\"Fruits\"");
        assertThat(content).contains("\"quantity\":5");
        assertThat(content).contains("\"name\":\"Banana\"");
        assertThat(content).contains("\"name\":\"Milk\"");
        assertThat(content).contains("\"category\":\"Dairy\"");
    }

    @Test
    void saveToFile_should_save_single_item_without_array() throws IOException {
        // Given - single item
        List<Item> groceryList = List.of(new Item("pomme", 2, "default"));

        File tempFile = tempDir.resolve("test_single.json").toFile();

        // When
        JsonHelper.saveToFile(groceryList, tempFile.getAbsolutePath());

        // Then
        String content = Files.readString(tempFile.toPath());
        assertThat(content).doesNotStartWith("["); // Should NOT be wrapped in array
        assertThat(content).doesNotEndWith("]");
        assertThat(content).contains("\"name\":\"pomme\"");
        assertThat(content).contains("\"quantity\":2");
        assertThat(content).contains("\"category\":\"default\"");
    }

    @Test
    void loadFromFile_should_load_categorized_format() throws IOException {
        // Given
        String jsonContent = "{\"Fruits\":[\"Apple 5\",\"Banana 3\"],\"Dairy\":[\"Milk 1\"]}";
        File tempFile = tempDir.resolve("test.json").toFile();
        Files.writeString(tempFile.toPath(), jsonContent);

        // When
        List<Item> result = JsonHelper.loadFromFile(tempFile.getAbsolutePath());

        // Then
        assertThat(result).hasSize(3);

        // Verify items are loaded with correct categories
        assertThat(result.stream().filter(i -> i.getCategory().equals("Fruits")).count()).isEqualTo(2);
        assertThat(result.stream().filter(i -> i.getCategory().equals("Dairy")).count()).isEqualTo(1);

        // Verify specific items
        assertThat(result.stream().anyMatch(i ->
                i.getName().equals("Apple") && i.getQuantity() == 5 && i.getCategory().equals("Fruits")
        )).isTrue();
    }

    @Test
    void loadFromFile_should_load_array_object_format() throws IOException {
        // Given - array format
        String jsonContent = "[{\"name\":\"Apple\",\"quantity\":5,\"category\":\"Fruits\"}]";
        File tempFile = tempDir.resolve("test_array.json").toFile();
        Files.writeString(tempFile.toPath(), jsonContent);

        // When
        List<Item> result = JsonHelper.loadFromFile(tempFile.getAbsolutePath());

        // Then
        assertThat(result).hasSize(1);
        Item item = result.get(0);
        assertThat(item.getName()).isEqualTo("Apple");
        assertThat(item.getQuantity()).isEqualTo(5);
        assertThat(item.getCategory()).isEqualTo("Fruits");
    }

    @Test
    void loadFromFile_should_load_single_object_format() throws IOException {
        // Given - single object format without array wrapper
        String jsonContent = "{\"name\":\"Apple\",\"quantity\":5,\"category\":\"Fruits\"}";
        File tempFile = tempDir.resolve("test_single.json").toFile();
        Files.writeString(tempFile.toPath(), jsonContent);

        // When
        List<Item> result = JsonHelper.loadFromFile(tempFile.getAbsolutePath());

        // Then
        assertThat(result).hasSize(1);
        Item item = result.get(0);
        assertThat(item.getName()).isEqualTo("Apple");
        assertThat(item.getQuantity()).isEqualTo(5);
        assertThat(item.getCategory()).isEqualTo("Fruits");
    }

    @Test
    void loadFromFile_should_load_simple_format() throws IOException {
        // Given
        String jsonContent = "[\"Apple:5\",\"Banana:3\"]";
        File tempFile = tempDir.resolve("test.json").toFile();
        Files.writeString(tempFile.toPath(), jsonContent);

        // When
        List<Item> result = JsonHelper.loadFromFile(tempFile.getAbsolutePath());

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.stream().anyMatch(i ->
                i.getName().equals("Apple") && i.getQuantity() == 5
        )).isTrue();
        assertThat(result.stream().anyMatch(i ->
                i.getName().equals("Banana") && i.getQuantity() == 3
        )).isTrue();
    }

    @Test
    void loadFromFile_should_return_empty_list_for_nonexistent_file() throws IOException {
        // Given
        String nonExistentPath = tempDir.resolve("nonexistent.json").toString();

        // When
        List<Item> result = JsonHelper.loadFromFile(nonExistentPath);

        // Then
        assertThat(result).isEmpty();
    }
}