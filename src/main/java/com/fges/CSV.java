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

    public void add(String item, int quantity) {
        groceryList.add(item + "," + quantity);
        saveToFile();
    }

    public void remove(String itemName) {
        groceryList = groceryList.stream()
                .filter(item -> !item.contains(itemName))
                .collect(Collectors.toList());
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



    public static void main(String[] args) {
        // Create an instance of the CSV class
        try {
            // Create a grocery.csv file or use an existing one
            String fileName = "grocery.csv";

            // Create an instance of the CSV class
            CSV csv = new CSV(fileName);

            // Example of adding items
            System.out.println("Adding items to the grocery list...");
            csv.add("Apple", 5);
            csv.add("Banana", 3);
            csv.add("Orange", 2);

            // Example of listing items
            System.out.println("\nCurrent grocery list:");
            List<String> items = csv.list();
            for (String item : items) {
                System.out.println(item);
            }

            // Example of removing an item
            System.out.println("\nRemoving Banana from the list...");
            csv.remove("Banana");

            // Listing items after removal
            System.out.println("\nUpdated grocery list:");
            items = csv.list();
            for (String item : items) {
                System.out.println(item);
            }

            // Verify file contents
            System.out.println("\nFile content verification:");
            if (Files.exists(Paths.get(fileName))) {
                List<String> fileContent = Files.readAllLines(Paths.get(fileName));
                for (String line : fileContent) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}