package com.fges.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fges.GroceryItem;

/**
 * Utility class for handling CSV input/output operations related to grocery items.
 * This class provides methods for loading grocery list items from a CSV file
 and saving them back to a CSV file in a standardized format.
 */
public class CsvHelper {

    private static final String HEADER = "GroceryItem,Quantité,Catégorie";

    /**
     * Loads grocery items from the specified CSV file.
     * If the file does not exist, an empty list is returned.
     */
    public static List<GroceryItem> loadFromFile(String fileName) throws IOException {
        List<GroceryItem> groceryList = new ArrayList<>();

        if (!Files.exists(Paths.get(fileName))) {
            return groceryList;
        }

        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Skip header line
        boolean isFirstLine = true;

        for (String line : lines) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            parseAndAddItem(line, groceryList);
        }

        return groceryList;
    }

    /**
     * Saves the given list of grocery items into the specified CSV file.
     *
     * The file is overwritten if it already exists. A header line is written first,
     * followed by one line per item.
     */
    public static void saveToFile(List<GroceryItem> groceryList, String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);

        lines.addAll(groceryList.stream()
                .map(CsvHelper::formatItemLine)
                .collect(Collectors.toList()));

        Files.write(Paths.get(fileName), lines);
    }

    /**
     Formats a single GroceryItem object into a CSV-compatible string.
     */
    private static String formatItemLine(GroceryItem groceryItem) {
        return groceryItem.getName() + "," + groceryItem.quantity + "," + groceryItem.getCategory();
    }

    /**
     * Parses a single line from the CSV file and adds the resulting GroceryItem to the provided list.
     * Handles errors such as incorrect format or number parsing issues
     */
    private static void parseAndAddItem(String line, List<GroceryItem> groceryList) {
        String[] parts = line.split(",");
        if (parts.length >= 2) {
            try {
                String itemName = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());

                String category = "default";
                if (parts.length >= 3) {
                    category = parts[2].trim();
                }

                groceryList.add(new GroceryItem(itemName, quantity, category));
            } catch (NumberFormatException e) {
                System.err.println("Invalid format in the file : " + line);
            }
        } else {
            System.err.println("Line ignored (wrong format) : " + line);
        }
    }
}
