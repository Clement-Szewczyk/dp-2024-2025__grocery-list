package com.fges.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.Item;

public class JsonHelper {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void saveToFile(List<Item> groceryList, String fileName) throws IOException {
        if (groceryList.size() == 1) {
            // Sauvegarder un seul objet sans tableau
            Item item = groceryList.get(0);
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("name", item.getName());
            itemMap.put("category", item.getCategory());
            itemMap.put("quantity", item.getQuantity());
            MAPPER.writeValue(new File(fileName), itemMap);
        } else {
            // Code pour plusieurs éléments
            List<Map<String, Object>> itemsToSave = new ArrayList<>();
            for (Item item : groceryList) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("name", item.getName());
                itemMap.put("category", item.getCategory());
                itemMap.put("quantity", item.getQuantity());
                itemsToSave.add(itemMap);
            }
            MAPPER.writeValue(new File(fileName), itemsToSave);
        }
    }

    private static Map<String, List<String>> categorizeItems(List<Item> groceryList) {
        Map<String, List<String>> categorizedItems = new HashMap<>();

        for (Item item : groceryList) {
            String category = item.getCategory();
            String itemWithQuantity = item.getName() + " " + item.quantity;

            categorizedItems.computeIfAbsent(category, k -> new ArrayList<>())
                    .add(itemWithQuantity);
        }

        return categorizedItems;
    }

    public static List<Item> loadFromFile(String fileName) throws IOException {
        List<Item> groceryList = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return groceryList;
        }

        // Essayer chaque format séquentiellement
        if (!tryLoadCategorizedFormat(file, groceryList) &&
                !tryLoadObjectFormat(file, groceryList) &&
                !tryLoadSimpleFormat(file, groceryList)) {
            throw new IOException("Format JSON non supporté dans: " + fileName);
        }

        return groceryList;
    }

    private static boolean tryLoadCategorizedFormat(File file, List<Item> groceryList) {
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

    private static boolean tryLoadObjectFormat(File file, List<Item> groceryList) {
        try {
            // Try loading as array first
            try {
                List<Map<String, Object>> loadedItems = MAPPER.readValue(
                        file, new TypeReference<>() {});

                for (Map<String, Object> itemMap : loadedItems) {
                    parseItemMap(itemMap, groceryList);
                }
                return true;
            } catch (Exception arrayParseError) {
                // If not array, try single object
                Map<String, Object> singleItem = MAPPER.readValue(file, new TypeReference<Map<String, Object>>() {});
                parseItemMap(singleItem, groceryList);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
    private static boolean tryLoadSimpleFormat(File file, List<Item> groceryList) {
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

    // Méthodes helpers pour le parsing
    private static void parseItemString(String itemString, String category, List<Item> groceryList) {
        try {
            int lastSpaceIndex = itemString.lastIndexOf(" ");
            if (lastSpaceIndex > 0) {
                String name = itemString.substring(0, lastSpaceIndex).trim();
                int quantity = Integer.parseInt(itemString.substring(lastSpaceIndex + 1).trim());
                groceryList.add(new Item(name, quantity, category));
            }
        } catch (Exception e) {
            System.err.println("Format invalide: " + itemString);
        }
    }

    private static void parseItemMap(Map<String, Object> itemMap, List<Item> groceryList) {
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

            groceryList.add(new Item(itemName, quantity, category));
        } catch (Exception ex) {
            System.err.println("Format invalide: " + itemMap);
        }
    }

    private static void parseSimpleEntry(String entry, List<Item> groceryList) {
        try {
            String[] parts = entry.split(":");
            if (parts.length == 2) {
                String name = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());
                groceryList.add(new Item(name, quantity, "default"));
            }
        } catch (NumberFormatException exc) {
            System.err.println("Format invalide: " + entry);
        }
    }
}