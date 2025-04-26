package com.fges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fges.dao.GroceryListDAO;

class GroceryListManagerTest {

    private GroceryListDAO mockDao;
    private GroceryListManager manager;

    @BeforeEach
    void setUp() {
        mockDao = mock(GroceryListDAO.class);
        manager = new GroceryListManager(mockDao);
    }

    @Test
    void initialize_should_load_from_file() throws IOException {
        // Given
        doNothing().when(mockDao).load(any());

        // When
        manager.initialize();

        // Then
        verify(mockDao, times(1)).load(any());
    }

    @Test
    void add_should_add_new_item_with_correct_values() {
        // When
        manager.add("Apple", 5, "Fruits");

        // Then
        List<GroceryItem> groceryList = manager.getGroceryList();
        assertThat(groceryList).hasSize(1);
        GroceryItem groceryItem = groceryList.get(0);
        assertThat(groceryItem.getName()).isEqualTo("Apple");
        assertThat(groceryItem.getQuantity()).isEqualTo(5);
        assertThat(groceryItem.getCategory()).isEqualTo("Fruits");
    }

    @Test
    void add_should_increase_quantity_of_existing_item() {
        // Given
        manager.add("Apple", 5, "Fruits");

        // When
        manager.add("Apple", 3, "Fruits");

        // Then
        List<GroceryItem> groceryList = manager.getGroceryList();
        assertThat(groceryList).hasSize(1);
        GroceryItem groceryItem = groceryList.get(0);
        assertThat(groceryItem.getQuantity()).isEqualTo(8);
    }

    @Test
    void add_should_add_separate_items_when_categories_differ() {
        // When
        manager.add("Apple", 5, "Fruits");
        manager.add("Apple", 3, "Organic");

        // Then
        List<GroceryItem> groceryList = manager.getGroceryList();
        assertThat(groceryList).hasSize(2);
        assertThat(groceryList.get(0).getCategory()).isEqualTo("Fruits");
        assertThat(groceryList.get(1).getCategory()).isEqualTo("Organic");
    }

    @Test
    void remove_with_quantity_should_decrease_item_quantity() {
        // Given
        manager.add("Apple", 5, "Fruits");

        // When
        manager.remove("Apple", 2);

        // Then
        List<GroceryItem> groceryList = manager.getGroceryList();
        assertThat(groceryList).hasSize(1);
        assertThat(groceryList.get(0).getQuantity()).isEqualTo(3);
    }

    @Test
    void remove_with_quantity_should_remove_item_when_quantity_becomes_zero() {
        // Given
        manager.add("Apple", 5, "Fruits");

        // When
        manager.remove("Apple", 5);

        // Then
        List<GroceryItem> groceryList = manager.getGroceryList();
        assertThat(groceryList).isEmpty();
    }

    @Test
    void remove_without_quantity_should_remove_all_items_with_name() {
        // Given
        manager.add("Apple", 5, "Fruits");
        manager.add("Apple", 3, "Organic");
        manager.add("Banana", 2, "Fruits");

        // When
        manager.remove("Apple");

        // Then
        List<GroceryItem> groceryList = manager.getGroceryList();
        assertThat(groceryList).hasSize(1);
        assertThat(groceryList.get(0).getName()).isEqualTo("Banana");
    }

    @Test
    void saveToFile_should_call_dao_save() throws IOException {
        // Given
        manager.add("Apple", 5, "Fruits");
        ArgumentCaptor<List<GroceryItem>> listCaptor = ArgumentCaptor.forClass(List.class);

        // When
        manager.saveToFile();

        // Then
        verify(mockDao).save(listCaptor.capture());
        List<GroceryItem> savedList = listCaptor.getValue();
        assertThat(savedList).hasSize(1);
        assertThat(savedList.get(0).getName()).isEqualTo("Apple");
    }

    @Test
    void loadFromFile_should_replace_existing_items() throws IOException {
        // Given
        manager.add("Initial", 1, "Test");

        List<GroceryItem> loadedGroceryItems = new ArrayList<>();
        loadedGroceryItems.add(new GroceryItem("Loaded", 10, "Test"));

        doAnswer(invocation -> {
            List<GroceryItem> list = invocation.getArgument(0);
            list.clear();
            list.addAll(loadedGroceryItems);
            return null;
        }).when(mockDao).load(any());

        // When
        manager.loadFromFile();

        // Then
        List<GroceryItem> groceryList = manager.getGroceryList();
        assertThat(groceryList).hasSize(1);
        assertThat(groceryList.get(0).getName()).isEqualTo("Loaded");
    }
}