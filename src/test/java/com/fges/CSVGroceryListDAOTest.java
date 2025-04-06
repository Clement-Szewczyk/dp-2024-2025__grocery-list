/*
package com.fges;


import com.fges.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CSVGroceryListDAOTest {

    private GroceryListDAO groceryListDAO;
    private List<Item> groceryList;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        Path tempFile = tempDir.resolve("test.csv");
        groceryListDAO = new CSVGroceryListDAO(tempFile.toString());
        groceryList = new ArrayList<>();
        groceryList.add(new Item("Pomme", 3, "Fruits"));
        groceryList.add(new Item("Carotte", 2, "Légumes"));
    }

    @Test
    void should_always_pass() {
        assertThat(true).isTrue();
    }

    @Test
    void should_save_items_to_CSV() throws IOException {
        // Enregistrer les éléments dans un fichier CSV
        groceryListDAO.save(groceryList);

        Path tempFile = tempDir.resolve("test.csv");
        assertThat(Files.exists(tempFile)).isTrue();

        String content = new String(Files.readAllBytes(tempFile));
        assertThat(content).contains("Pomme,3,Fruits");
        assertThat(content).contains("Carotte,2,Légumes");
    }

    @Test
    void should_load_items_from_CSV() {
        groceryListDAO.save(groceryList);

        // Charger les éléments depuis le fichier CSV
        List<Item> loadedItems = new ArrayList<>();
        groceryListDAO.load(loadedItems);

        assertThat(loadedItems).hasSize(2);
        assertThat(loadedItems.get(0).getName()).isEqualTo("Pomme");
        assertThat(loadedItems.get(1).getName()).isEqualTo("Carotte");
    }
}

*/
