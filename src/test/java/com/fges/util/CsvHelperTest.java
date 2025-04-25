package com.fges.util;

import com.fges.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvHelperTest {

    @TempDir
    Path tempDir;

    @Test
    void saveToFile_should_create_file_with_header_and_data() throws IOException {
        // Given
        List<Item> groceryList = Arrays.asList(
                new Item("Apple", 5, "Fruits"),
                new Item("Milk", 1, "Dairy")
        );

        Path tempFile = tempDir.resolve("test.csv");

        // When
        CsvHelper.saveToFile(groceryList, tempFile.toString());

        // Then
        List<String> lines = Files.readAllLines(tempFile);

        // Check header
        assertThat(lines.get(0)).isEqualTo("Item,Quantité,Catégorie");

        // Check data rows
        assertThat(lines).contains("Apple,5,Fruits");
        assertThat(lines).contains("Milk,1,Dairy");
    }

    @Test
    void loadFromFile_should_read_header_and_data() throws IOException {
        // Given
        String csvContent = "Item,Quantité,Catégorie\nApple,5,Fruits\nMilk,1,Dairy";
        Path tempFile = tempDir.resolve("test.csv");
        Files.writeString(tempFile, csvContent);

        // When
        List<Item> result = CsvHelper.loadFromFile(tempFile.toString());

        // Then
        assertThat(result).hasSize(2);

        Item apple = result.stream().filter(i -> i.getName().equals("Apple")).findFirst().orElse(null);
        assertThat(apple).isNotNull();
        assertThat(apple.getQuantity()).isEqualTo(5);
        assertThat(apple.getCategory()).isEqualTo("Fruits");

        Item milk = result.stream().filter(i -> i.getName().equals("Milk")).findFirst().orElse(null);
        assertThat(milk).isNotNull();
        assertThat(milk.getQuantity()).isEqualTo(1);
        assertThat(milk.getCategory()).isEqualTo("Dairy");
    }

    @Test
    void loadFromFile_should_handle_missing_category() throws IOException {
        // Given
        String csvContent = "Item,Quantité\nApple,5";
        Path tempFile = tempDir.resolve("test.csv");
        Files.writeString(tempFile, csvContent);

        // When
        List<Item> result = CsvHelper.loadFromFile(tempFile.toString());

        // Then
        assertThat(result).hasSize(1);
        Item item = result.get(0);
        assertThat(item.getName()).isEqualTo("Apple");
        assertThat(item.getQuantity()).isEqualTo(5);
        assertThat(item.getCategory()).isEqualTo("default"); // Default category when not specified
    }

    @Test
    void loadFromFile_should_ignore_invalid_lines() throws IOException {
        // Given
        String csvContent = "Item,Quantité,Catégorie\nApple,notANumber,Fruits\nBanana,3,Fruits";
        Path tempFile = tempDir.resolve("test.csv");
        Files.writeString(tempFile, csvContent);

        // When
        List<Item> result = CsvHelper.loadFromFile(tempFile.toString());

        // Then
        assertThat(result).hasSize(1);
        Item item = result.get(0);
        assertThat(item.getName()).isEqualTo("Banana");
    }

    @Test
    void loadFromFile_should_return_empty_list_for_nonexistent_file() throws IOException {
        // Given
        String nonExistentPath = tempDir.resolve("nonexistent.csv").toString();

        // When
        List<Item> result = CsvHelper.loadFromFile(nonExistentPath);

        // Then
        assertThat(result).isEmpty();
    }
}