package com.fges;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSV {
    private final String fileName;
    private List<String> groceryList;

    public CSV(String fileName) {
        this.fileName = fileName;
        this.groceryList = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile() {
        Path filePath = Paths.get(fileName);
        try {
            if (Files.exists(filePath)) {
                groceryList = Files.readAllLines(filePath);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try {
            Files.write(Paths.get(fileName), groceryList);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void add(String itemName, int quantity) {
        boolean itemFound = false;
        for (int i = 0; i < groceryList.size(); i++) {
            String currentItem = groceryList.get(i);
            if (currentItem.startsWith(itemName)) {
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
    public void remove(String itemName) {
        remove(itemName, 0);
    }

    public void remove(String itemName, int quantity) {
        if (quantity <= 0) {
            groceryList = groceryList.stream()
                    .filter(item -> !item.startsWith(itemName + ","))
                    .collect(Collectors.toList());
        } else {
            boolean itemFound = false;
            for (int i = 0; i < groceryList.size(); i++) {
                String currentItem = groceryList.get(i);
                if (currentItem.startsWith(itemName)) {
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

    public List<String> list() {
        return new ArrayList<>(groceryList);
    }

    @Override
    public String toString() {
        return "CSV{" +
                "fileName='" + fileName + '\'' +
                ", groceryList=" + groceryList +
                '}';
    }

}