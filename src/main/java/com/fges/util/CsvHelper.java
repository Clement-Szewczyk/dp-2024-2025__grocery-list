package com.fges.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fges.Item;

/**
 * Utility class for handling CSV input/output operations related to grocery items.
 * This class provides methods for loading grocery list items from a CSV file
 * and saving them back to a CSV file in a standardized format.
 *
 * The expected format of the CSV file is:
 *
 * Item,Quantité,Catégorie
 *
 * The first line is assumed to be a header and is skipped during loading.
 *
 * This class assumes that items are stored and read as {@link Item} objects,
 * and includes basic error handling for malformed lines or number conversion issues.
 *
 * @author Szewczyk Clément,
 *         Stievenard Emma,
 *         Laurency Yuna
 */
public class CsvHelper {

    private static final String HEADER = "Item,Quantité,Catégorie";

    /**
     * Loads grocery items from the specified CSV file.
     *
     * If the file does not exist, an empty list is returned.
     * The method expects each line (after the header) to follow the format:
     * {@code itemName,quantity,category}
     *
     * @param fileName The path to the CSV file
     * @return A list of {@link Item} objects loaded from the file
     * @throws IOException If an I/O error occurs while reading the file
     */
    public static List<Item> loadFromFile(String fileName) throws IOException {
        List<Item> groceryList = new ArrayList<>();

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
     * followed by one line per item formatted as:
     * {@code itemName,quantity,category}
     *
     * @param groceryList The list of {@link Item} objects to save
     * @param fileName The path to the CSV file to write
     * @throws IOException If an I/O error occurs while writing the file
     */
    public static void saveToFile(List<Item> groceryList, String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);

        lines.addAll(groceryList.stream()
                .map(CsvHelper::formatItemLine)
                .collect(Collectors.toList()));

        Files.write(Paths.get(fileName), lines);
    }

    /**
     * Formats a single {@link Item} object into a CSV-compatible string.
     *
     * @param item The item to format
     * @return A string representing the item in CSV format
     */
    private static String formatItemLine(Item item) {
        return item.getName() + "," + item.quantity + "," + item.getCategory();
    }

    /**
     * Parses a single line from the CSV file and adds the resulting {@link Item}
     * to the provided list.
     *
     * Handles errors such as incorrect format or number parsing issues
     * by logging to {@code System.err}.
     *
     * @param line The CSV line to parse
     * @param groceryList The list to which the item will be added
     */
    private static void parseAndAddItem(String line, List<Item> groceryList) {
        String[] parts = line.split(",");
        if (parts.length >= 2) {
            try {
                String itemName = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());

                String category = "default";
                if (parts.length >= 3) {
                    category = parts[2].trim();
                }

                groceryList.add(new Item(itemName, quantity, category));
            } catch (NumberFormatException e) {
                System.err.println("Invalid format in the file : " + line);
            }
        } else {
            System.err.println("Line ignored (wrong format) : " + line);
        }
    }
}
