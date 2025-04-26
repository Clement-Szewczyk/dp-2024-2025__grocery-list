package com.fges.dao;

import java.io.IOException;
import java.util.List;

import com.fges.GroceryItem;
import com.fges.util.CsvHelper;

/**
 * Implementation of the {@link GroceryListDAO} interface that handles
 * persistence of grocery list data using a CSV file.
 *
 * This class delegates the actual CSV parsing and writing logic to the
 * {@link CsvHelper} utility class. It ensures that the data from the file
 * is properly loaded into the provided list and that the current state of
 * the list can be saved back to the same file.
 *
 * This implementation provides a clean separation between business logic
 * and data access logic, following the DAO pattern.
 *
 * @author Szewczyk Clément,
 *         Stievenard Emma,
 *         Laurency Yuna
 */
public class CSVGroceryListDAO implements GroceryListDAO {

    /**
     * The path to the CSV file used for reading and writing grocery list data.
     */
    private final String fileName;

    /**
     * Constructs a new {@code CSVGroceryListDAO} instance with the specified file path.
     *
     * @param fileName The name or path of the CSV file to use for persistence
     */
    public CSVGroceryListDAO(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Loads grocery items from the CSV file and populates the provided list.
     *
     * The list is cleared before new items are added to it.
     *
     * @param groceryList The list to populate with loaded items
     * @throws IOException If an I/O error occurs while reading the file
     */
    @Override
    public void load(List<GroceryItem> groceryList) throws IOException {
        List<GroceryItem> loadedGroceryItems = CsvHelper.loadFromFile(fileName);
        groceryList.clear();
        groceryList.addAll(loadedGroceryItems);
    }

    /**
     * Saves the provided list of grocery items to the CSV file.
     *
     * The file is overwritten with the current contents of the list.
     *
     * @param groceryList The list of items to be saved
     * @throws IOException If an I/O error occurs while writing the file
     */
    @Override
    public void save(List<GroceryItem> groceryList) throws IOException {
        CsvHelper.saveToFile(groceryList, fileName);
    }
}
