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

import static org.assertj.core.api.Assertions.assertThat;

public class CSVTest {
    @TempDir
    Path tempDir;

    private Path tempFile;
    private CSV csv;

    @BeforeEach
    void setUp() throws IOException {
        // Créer un fichier temporaire
        tempFile = tempDir.resolve("grocery.csv");

        // Initialisation de la liste de courses
        List<Item> groceryList = new ArrayList<>();
        groceryList.add(new Item("apple", 5, "Fruits"));
        groceryList.add(new Item("banana", 3, "Fruits"));

        // Sauvegarder les éléments dans le fichier CSV
        csv = new CSV();
        csv.save(groceryList, tempFile.toString());
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
        csv.load(loadedList, tempFile.toString());

        assertThat(loadedList).hasSize(2);
        assertThat(loadedList.get(0).getName()).isEqualTo("apple");
        assertThat(loadedList.get(1).getName()).isEqualTo("banana");
    }

    @Test
    void should_save_items_to_file() throws IOException {
        List<Item> newGroceryList = new ArrayList<>();
        newGroceryList.add(new Item("orange", 4, "Fruits"));

        csv.save(newGroceryList, tempFile.toString());

        List<Item> loadedList = new ArrayList<>();
        csv.load(loadedList, tempFile.toString());

        assertThat(loadedList).hasSize(1);
        assertThat(loadedList.get(0).getName()).isEqualTo("orange");
        assertThat(loadedList.get(0).getQuantity()).isEqualTo(4);
    }

    @Test
    void should_not_load_if_file_does_not_exist() throws IOException {
        Path nonExistentFile = tempDir.resolve("non_existent_file.csv");

        List<Item> loadedList = new ArrayList<>();
        csv.load(loadedList, nonExistentFile.toString());

        assertThat(loadedList).isEmpty();
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