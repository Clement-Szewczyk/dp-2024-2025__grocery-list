package com.fges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CSV class for handling the persistence of grocery list items.
 * This class is responsible for saving and loading grocery list data to/from CSV files.
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
public class CSV {

    /**
     * Saves a list of grocery items to a CSV file.
     * The file will contain a header row followed by one row per grocery item.
     * Each row contains the item name, quantity, and category separated by commas.
     *
     * @param groceryList The list of Item objects to be saved
     * @param fileName The name of the file where the data will be saved
     * @throws IOException If an I/O error occurs during the file writing process
     */

    public void save(List<Item> groceryList, String fileName) throws IOException {
        List<String> lines = new ArrayList<>();

        // Add header row with column names
        lines.add("Item, Quantité, Catégorie");

        // Convert each Item object to a CSV formatted string and add to lines list
        lines.addAll(groceryList.stream()
                .map(item -> item.getName() + "," + item.quantity + "," + item.getCategory())
                .collect(Collectors.toList()));

        // Write all lines to the specified file
        Files.write(Paths.get(fileName), lines);
    }

    /**
     * Loads grocery items from a CSV file into the provided list.
     * The method clears the existing list before loading new items.
     * If the file doesn't exist, the method returns without making changes.
     * The first line of the file is expected to be a header and is skipped.
     * Each subsequent line should contain an item name, quantity, and optionally a category.
     *
     * @param groceryList The list to be populated with loaded Item objects
     * @param fileName The name of the file to load data from
     * @throws IOException If an I/O error occurs during the file reading process
     */
    public void load(List<Item> groceryList, String fileName) throws IOException {
        // Clear the existing list to prepare for loading new items
        groceryList.clear();

        // Check if the file exists, return if it doesn't
        if (!Files.exists(Paths.get(fileName))) {
            return;
        }

        // Read all lines from the file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Flag to identify and skip the header line
        boolean isFirstLine = true;

        // Process each line of the file
        for (String line : lines) {
            // Skip the header line
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            // Split the line into parts using comma as delimiter
            String[] parts = line.split(",");
            // Check if there are at least 2 parts (name and quantity)
            if (parts.length >= 2) {
                try {
                    // Extract and trim the item name
                    String itemName = parts[0].trim();

                    // Parse and convert the quantity string to an integer
                    int quantity = Integer.parseInt(parts[1].trim());

                    // Set default category and override if available in the CSV
                    String category = "default";
                    if (parts.length >= 3) {
                        category = parts[2].trim();
                    }

                    // Create a new Item object and add it to the grocery list
                    groceryList.add(new Item(itemName, quantity, category));
                } catch (NumberFormatException e) {
                    // Log error if quantity cannot be parsed as an integer
                    System.err.println("Invalid format in file : " + line);
                }
            } else {
                // Log error if the line doesn't have the minimum required fields
                System.err.println("Line ignored (wrong format) : " + line);
            }
        }
    }
}