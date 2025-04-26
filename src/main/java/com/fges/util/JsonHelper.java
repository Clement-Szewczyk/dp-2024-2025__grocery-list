package com.fges.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.GroceryItem;

/**
 * Utility class for handling JSON-based input/output operations related to grocery list items.
 *
 * Supports multiple JSON formats:
 *
 * Single item as an object
 * Multiple items as an array of objects
 * Simple list format (e.g., "Milk:2")
 * Categorized map format (e.g., {"Fruits": ["Apple 2", "Banana 3"]})
 *
 * This class centralizes all JSON reading/writing logic to keep the DAO layer clean.
 *
 * Uses the Jackson library for parsing and writing JSON files.
 *
 * @author Szewczyk Clément,
 *         Stievenard Emma,
 *         Laurency Yuna
 */
public class JsonHelper {

    /**
     * Shared Jackson object mapper instance.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Saves the given grocery list to a JSON file.
     * If the list contains only one item, it is stored as a single JSON object.
     * Otherwise, the list is saved as an array of objects.
     *
     * @param groceryList the list of items to be saved
     * @param fileName the file path where to save the data
     * @throws IOException if an error occurs during writing
     */
    public static void saveToFile(List<GroceryItem> groceryList, String fileName) throws IOException {
        if (groceryList.size() == 1) {
            GroceryItem groceryItem = groceryList.get(0);
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("name", groceryItem.getName());
            itemMap.put("category", groceryItem.getCategory());
            itemMap.put("quantity", groceryItem.getQuantity());
            MAPPER.writeValue(new File(fileName), itemMap);
        } else {
            List<Map<String, Object>> itemsToSave = new ArrayList<>();
            for (GroceryItem groceryItem : groceryList) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("name", groceryItem.getName());
                itemMap.put("category", groceryItem.getCategory());
                itemMap.put("quantity", groceryItem.getQuantity());
                itemsToSave.add(itemMap);
            }
            MAPPER.writeValue(new File(fileName), itemsToSave);
        }
    }

    /**
     * Loads a grocery list from a JSON file.
     * Tries multiple formats and returns the corresponding list of items.
     *
     * @param fileName the file path to load data from
     * @return a list of items parsed from the file
     * @throws IOException if the file is unreadable or unsupported format
     */
    public static List<GroceryItem> loadFromFile(String fileName) throws IOException {
        List<GroceryItem> groceryList = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return groceryList;
        }

        if (!tryLoadCategorizedFormat(file, groceryList) &&
                !tryLoadObjectFormat(file, groceryList) &&
                !tryLoadSimpleFormat(file, groceryList)) {
            throw new IOException("Format JSON non supporté dans: " + fileName);
        }

        return groceryList;
    }

    /**
     * Attempts to load items from a categorized format: Map<Category, List<ItemName + Quantity>>.
     */
    private static boolean tryLoadCategorizedFormat(File file, List<GroceryItem> groceryList) {
        try {
            Map<String, List<String>> categorizedItems = MAPPER.readValue(
                    file, new TypeReference<>() {});

            for (Map.Entry<String, List<String>> entry : categorizedItems.entrySet()) {
                String category = entry.getKey();
                for (String itemString : entry.getValue()) {
                    parseItemString(itemString, category, groceryList);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Attempts to load items from a format that is either:
     * - a list of maps (array of objects)
     * - a single map (single object)
     */
    private static boolean tryLoadObjectFormat(File file, List<GroceryItem> groceryList) {
        try {
            // Try array of items
            try {
                List<Map<String, Object>> loadedItems = MAPPER.readValue(
                        file, new TypeReference<>() {});

                for (Map<String, Object> itemMap : loadedItems) {
                    parseItemMap(itemMap, groceryList);
                }
                return true;
            } catch (Exception arrayParseError) {
                // Fallback to single object
                Map<String, Object> singleItem = MAPPER.readValue(file, new TypeReference<>() {});
                parseItemMap(singleItem, groceryList);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Attempts to load items from a simplified format: List<String> with entries like "Milk:2".
     */
    private static boolean tryLoadSimpleFormat(File file, List<GroceryItem> groceryList) {
        try {
            List<String> loadedList = MAPPER.readValue(
                    file, MAPPER.getTypeFactory().constructCollectionType(List.class, String.class));

            for (String entry : loadedList) {
                parseSimpleEntry(entry, groceryList);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Parses an entry in the format "name quantity" and adds it to the list.
     */
    private static void parseItemString(String itemString, String category, List<GroceryItem> groceryList) {
        try {
            int lastSpaceIndex = itemString.lastIndexOf(" ");
            if (lastSpaceIndex > 0) {
                String name = itemString.substring(0, lastSpaceIndex).trim();
                int quantity = Integer.parseInt(itemString.substring(lastSpaceIndex + 1).trim());
                groceryList.add(new GroceryItem(name, quantity, category));
            }
        } catch (Exception e) {
            System.err.println("Format invalide: " + itemString);
        }
    }

    /**
     * Parses a JSON object as a map and adds it to the list.
     */
    private static void parseItemMap(Map<String, Object> itemMap, List<GroceryItem> groceryList) {
        try {
            String itemName = (String) itemMap.get("name");
            int quantity;

            if (itemMap.get("quantity") instanceof Integer) {
                quantity = (Integer) itemMap.get("quantity");
            } else {
                quantity = Integer.parseInt(itemMap.get("quantity").toString());
            }

            String category = itemMap.containsKey("category")
                    ? (String) itemMap.get("category")
                    : "default";

            groceryList.add(new GroceryItem(itemName, quantity, category));
        } catch (Exception ex) {
            System.err.println("Format invalide: " + itemMap);
        }
    }

    /**
     * Parses a simplified entry like "Bread:1" and adds it to the list.
     */
    private static void parseSimpleEntry(String entry, List<GroceryItem> groceryList) {
        try {
            String[] parts = entry.split(":");
            if (parts.length == 2) {
                String name = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());
                groceryList.add(new GroceryItem(name, quantity, "default"));
            }
        } catch (NumberFormatException exc) {
            System.err.println("Format invalide: " + entry);
        }
    }

    /**
     * Internal utility used only by saveToFile to group items by category.
     * (Currently unused externally.)
     */
    private static Map<String, List<String>> categorizeItems(List<GroceryItem> groceryList) {
        Map<String, List<String>> categorizedItems = new HashMap<>();

        for (GroceryItem groceryItem : groceryList) {
            String category = groceryItem.getCategory();
            String itemWithQuantity = groceryItem.getName() + " " + groceryItem.quantity;

            categorizedItems.computeIfAbsent(category, k -> new ArrayList<>())
                    .add(itemWithQuantity);
        }

        return categorizedItems;
    }
}
