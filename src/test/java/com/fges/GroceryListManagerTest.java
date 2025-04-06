package com.fges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

class GroceryListManagerTest {
    @TempDir
    Path tempDir;
    private GroceryListManager manager;

    @BeforeEach
    void setUp() {
        String fileName = this.tempDir.resolve("testfile.csv").toString();
        this.manager = new GroceryListManager(fileName, "csv");
        this.manager.initialize();
    }

    @Test
    void should_add_item_with_default_quantity() {
        this.manager.add("Pomme");
        this.manager.list();
        assertThat(this.manager.groceryList).hasSize(1);
        assertThat(this.manager.groceryList.get(0).getName()).isEqualTo("Pomme");
        assertThat(this.manager.groceryList.get(0).getQuantity()).isEqualTo(1);
    }

    @Test
    void should_add_item_with_specific_quantity() {
        this.manager.add("Pomme", 3, "Fruits");
        this.manager.list();
        assertThat(this.manager.groceryList).hasSize(1);
        assertThat(this.manager.groceryList.get(0).getName()).isEqualTo("Pomme");
        assertThat(this.manager.groceryList.get(0).getQuantity()).isEqualTo(3);
        assertThat(this.manager.groceryList.get(0).getCategory()).isEqualTo("Fruits");
    }

    @Test
    void should_remove_item_by_name() {
        this.manager.add("Pomme", 3, "Fruits");
        this.manager.remove("Pomme");
        this.manager.list();
        assertThat(this.manager.groceryList).isEmpty();
    }

    @Test
    void should_remove_item_by_name_and_quantity() {
        this.manager.add("Pomme", 5, "Fruits");
        this.manager.remove("Pomme", 3);
        this.manager.list();
        assertThat(this.manager.groceryList).hasSize(1);
        assertThat(this.manager.groceryList.get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    void should_not_allow_missing_arguments_for_command() {
        int result = this.manager.handleCommand("add", new String[]{"Pomme"}, "Fruits");
        assertThat(result).isEqualTo(1);
    }

    @Test
    void should_add_item_to_specific_category() {
        this.manager.add("Pomme", 2, "Fruits");
        this.manager.add("Carrotte", 3, "Légumes");
        this.manager.list();
        assertThat(this.manager.groceryList).hasSize(2);
        assertThat(this.manager.groceryList.get(0).getCategory()).isEqualTo("Fruits");
        assertThat(this.manager.groceryList.get(1).getCategory()).isEqualTo("Légumes");
    }

    @Test
    void should_save_after_modifications() throws IOException {
        this.manager.add("Pomme", 2, "Fruits");
        this.manager.add("Carrotte", 3, "Légumes");
        this.manager.saveToFile();
        Path testFile = this.tempDir.resolve("testfile.csv");
        assertThat(Files.exists(testFile)).isTrue();
        String content = new String(Files.readAllBytes(testFile));
        assertThat(content).contains("Pomme,2,Fruits");
        assertThat(content).contains("Carrotte,3,Légumes");
    }
}
