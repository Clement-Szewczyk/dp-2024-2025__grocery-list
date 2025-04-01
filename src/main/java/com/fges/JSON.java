package com.fges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JSON extends GroceryFile {

    public JSON(String fileName) {
        super(fileName);
    }

    @Override
    protected void parseFileContents(List<String> fileContents) {
        // Implement JSON parsing logic here
        // This is a placeholder - actual implementation would depend on JSON structure
        groceryList = new ArrayList<>(fileContents);
    }

    @Override
    protected void saveToFile() {
        try {
            // Implement JSON formatting logic here
            Files.write(Paths.get(fileName), groceryList);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public void add(String itemName, int quantity) {
        // Implement JSON-specific add logic
        // This is a simplified implementation
        boolean itemFound = false;
        for (int i = 0; i < groceryList.size(); i++) {
            String currentItem = groceryList.get(i);
            if (currentItem.contains("\"" + itemName + "\"")) {
                // Update quantity in JSON
                // Simplified implementation
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            // Add new item in JSON format
            // Simplified implementation
        }
        saveToFile();
    }

    @Override
    public void remove(String itemName, int quantity) {
        // Implement JSON-specific remove logic
        saveToFile();
    }

    @Override
    public List<String> list() {
        // Format list for display
        return groceryList;
    }
}