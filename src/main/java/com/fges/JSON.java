package com.fges;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public void save(List<Item> groceryList, String fileName) throws IOException {
        // Group items by category
        Map<String, List<String>> categorizedItems = new HashMap<>();
        
        // Process each item in the grocery list
        for (Item item : groceryList) {
            String category = item.getCategory();
            String itemWithQuantity = item.getName() + " " + item.quantity;
            
            // Create category entry if it doesn't exist yet
            if (!categorizedItems.containsKey(category)) {
                categorizedItems.put(category, new ArrayList<>());
            }
            
            // Add the item to its category
            categorizedItems.get(category).add(itemWithQuantity);
        }
        
        // Write the map to JSON file
        OBJECT_MAPPER.writeValue(new File(fileName), categorizedItems);
    }

    public void load(List<Item> groceryList, String fileName) throws IOException {
        groceryList.clear();
        
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        try {
            // Try to read as new category-based format
            Map<String, List<String>> categorizedItems = OBJECT_MAPPER.readValue(
                    file,
                    new TypeReference<Map<String, List<String>>>() {}
            );
            
            // Process each category
            for (Map.Entry<String, List<String>> entry : categorizedItems.entrySet()) {
                String category = entry.getKey();
                List<String> items = entry.getValue();
                
                // Process each item in the category
                for (String itemString : items) {
                    try {
                        // Split the string into item name and quantity
                        // The format is "itemName quantity"
                        int lastSpaceIndex = itemString.lastIndexOf(" ");
                        if (lastSpaceIndex > 0) {
                            String name = itemString.substring(0, lastSpaceIndex).trim();
                            int quantity = Integer.parseInt(itemString.substring(lastSpaceIndex + 1).trim());
                            groceryList.add(new Item(name, quantity, category));
                        }
                    } catch (Exception e) {
                        System.err.println("Format invalide dans le fichier JSON : " + itemString);
                    }
                }
            }
        } catch (Exception e) {
            // Try the previous format as fallback
            try {
                List<Map<String, Object>> loadedItems = OBJECT_MAPPER.readValue(
                        file,
                        new TypeReference<List<Map<String, Object>>>() {}
                );
                
                for (Map<String, Object> itemMap : loadedItems) {
                    try {
                        String itemName = (String) itemMap.get("name");
                        int quantity;
                        
                        if (itemMap.get("quantity") instanceof Integer) {
                            quantity = (Integer) itemMap.get("quantity");
                        } else {
                            quantity = Integer.parseInt(itemMap.get("quantity").toString());
                        }
                        
                        String category = "default";
                        if (itemMap.containsKey("category")) {
                            category = (String) itemMap.get("category");
                        }
                        
                        groceryList.add(new Item(itemName, quantity, category));
                    } catch (Exception ex) {
                        System.err.println("Format invalide dans le fichier JSON : " + itemMap);
                    }
                }
            } catch (Exception ex) {
                // Try the oldest format as last resort
                try {
                    List<String> loadedList = OBJECT_MAPPER.readValue(
                            file,
                            OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, String.class)
                    );
                    
                    for (String entry : loadedList) {
                        String[] parts = entry.split(":");
                        if (parts.length == 2) {
                            try {
                                String name = parts[0].trim();
                                int quantity = Integer.parseInt(parts[1].trim());
                                groceryList.add(new Item(name, quantity, "default"));
                            } catch (NumberFormatException exc) {
                                System.err.println("Format invalide dans le fichier JSON : " + entry);
                            }
                        }
                    }
                } catch (Exception exc) {
                    System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
                }
            }
        }
    }
}