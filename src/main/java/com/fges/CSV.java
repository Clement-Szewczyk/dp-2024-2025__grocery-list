package com.fges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSV extends GroceryFile {

    public CSV(String fileName) {
        super(fileName);
    }

    @Override
    protected void parseFileContents(List<String> fileContents) {
        groceryList = new ArrayList<>(fileContents);
    }

    @Override
    protected void saveToFile() {
        try {
            Files.write(Paths.get(fileName), groceryList);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public void add(String itemName, int quantity) {
        boolean itemFound = false;
        for (int i = 0; i < groceryList.size(); i++) {
            String currentItem = groceryList.get(i);
            if (currentItem.startsWith(itemName + ",")) {
                int currentQuantity = Integer.parseInt(currentItem.split(",")[1]);
                groceryList.set(i, itemName + "," + (currentQuantity + quantity));
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            groceryList.add(itemName + "," + quantity);
        }
        saveToFile();
    }

    @Override
    public void remove(String itemName, int quantity) {
        if (quantity <= 0) {
            groceryList = groceryList.stream()
                    .filter(item -> !item.startsWith(itemName + ","))
                    .collect(Collectors.toList());
        } else {
            boolean itemFound = false;
            for (int i = 0; i < groceryList.size(); i++) {
                String currentItem = groceryList.get(i);
                if (currentItem.startsWith(itemName + ",")) {
                    int currentQuantity = Integer.parseInt(currentItem.split(",")[1]);
                    if (currentQuantity > quantity) {
                        groceryList.set(i, itemName + "," + (currentQuantity - quantity));
                    } else {
                        groceryList.remove(i);
                    }
                    itemFound = true;
                    break;
                }
            }
            if (!itemFound) {
                System.out.println("Item not found in the list.");
            }
        }
        saveToFile();
    }

    @Override
    public List<String> list() {
        return new ArrayList<>(groceryList);
    }
}