package com.fges.util;

import com.fges.GroceryItem;
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
        List<GroceryItem> groceryList = Arrays.asList(
                new GroceryItem("Apple", 5, "Fruits"),
                new GroceryItem("Milk", 1, "Dairy")
        );

        Path tempFile = tempDir.resolve("test.csv");

        // When
        CsvHelper.saveToFile(groceryList, tempFile.toString());

        // Then
        List<String> lines = Files.readAllLines(tempFile);

        // Check header
        assertThat(lines.get(0)).isEqualTo("GroceryItem,Quantité,Catégorie");

        // Check data rows
        assertThat(lines).contains("Apple,5,Fruits");
        assertThat(lines).contains("Milk,1,Dairy");
    }

    @Test
    void loadFromFile_should_read_header_and_data() throws IOException {
        // Given
        String csvContent = "GroceryItem,Quantité,Catégorie\nApple,5,Fruits\nMilk,1,Dairy";
        Path tempFile = tempDir.resolve("test.csv");
        Files.writeString(tempFile, csvContent);

        // When
        List<GroceryItem> result = CsvHelper.loadFromFile(tempFile.toString());

        // Then
        assertThat(result).hasSize(2);

        GroceryItem apple = result.stream().filter(i -> i.getName().equals("Apple")).findFirst().orElse(null);
        assertThat(apple).isNotNull();
        assertThat(apple.getQuantity()).isEqualTo(5);
        assertThat(apple.getCategory()).isEqualTo("Fruits");

        GroceryItem milk = result.stream().filter(i -> i.getName().equals("Milk")).findFirst().orElse(null);
        assertThat(milk).isNotNull();
        assertThat(milk.getQuantity()).isEqualTo(1);
        assertThat(milk.getCategory()).isEqualTo("Dairy");
    }

    @Test
    void loadFromFile_should_handle_missing_category() throws IOException {
        // Given
        String csvContent = "GroceryItem,Quantité\nApple,5";
        Path tempFile = tempDir.resolve("test.csv");
        Files.writeString(tempFile, csvContent);

        // When
        List<GroceryItem> result = CsvHelper.loadFromFile(tempFile.toString());

        // Then
        assertThat(result).hasSize(1);
        GroceryItem groceryItem = result.get(0);
        assertThat(groceryItem.getName()).isEqualTo("Apple");
        assertThat(groceryItem.getQuantity()).isEqualTo(5);
        assertThat(groceryItem.getCategory()).isEqualTo("default"); // Default category when not specified
    }

    @Test
    void loadFromFile_should_ignore_invalid_lines() throws IOException {
        // Given
        String csvContent = "GroceryItem,Quantité,Catégorie\nApple,notANumber,Fruits\nBanana,3,Fruits";
        Path tempFile = tempDir.resolve("test.csv");
        Files.writeString(tempFile, csvContent);

        // When
        List<GroceryItem> result = CsvHelper.loadFromFile(tempFile.toString());

        // Then
        assertThat(result).hasSize(1);
        GroceryItem groceryItem = result.get(0);
        assertThat(groceryItem.getName()).isEqualTo("Banana");
    }

    @Test
    void loadFromFile_should_return_empty_list_for_nonexistent_file() throws IOException {
        // Given
        String nonExistentPath = tempDir.resolve("nonexistent.csv").toString();

        // When
        List<GroceryItem> result = CsvHelper.loadFromFile(nonExistentPath);

        // Then
        assertThat(result).isEmpty();
    }
}