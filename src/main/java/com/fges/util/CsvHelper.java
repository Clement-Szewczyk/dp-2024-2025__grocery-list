package com.fges.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fges.Item;

public class CsvHelper {

    private static final String HEADER = "Item,Quantité,Catégorie";

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

    public static void saveToFile(List<Item> groceryList, String fileName) throws IOException {
        List<String> lines = new ArrayList<>();

        // Add header row
        lines.add(HEADER);

        // Add data rows
        lines.addAll(groceryList.stream()
                .map(CsvHelper::formatItemLine)
                .collect(Collectors.toList()));

        Files.write(Paths.get(fileName), lines);
    }

    private static String formatItemLine(Item item) {
        return item.getName() + "," + item.quantity + "," + item.getCategory();
    }

    private static void parseAndAddItem(String line, List<Item> groceryList) {
        String[] parts = line.split(",");
        if (parts.length >= 2) {
            try {
                String itemName = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());

                // Check if category exists
                String category = "default";
                if (parts.length >= 3) {
                    category = parts[2].trim();
                }

                groceryList.add(new Item(itemName, quantity, category));
            } catch (NumberFormatException e) {
                System.err.println("Format invalide dans le fichier : " + line);
            }
        } else {
            System.err.println("Ligne ignorée (mauvais format) : " + line);
        }
    }
}