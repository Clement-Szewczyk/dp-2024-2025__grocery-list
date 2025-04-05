package com.fges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSV {

    public void save(List<Item> groceryList, String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        
        // Add header row
        lines.add("Item, Quantité, Catégorie");
        
        // Add data rows
        lines.addAll(groceryList.stream()
                .map(item -> item.getName() + "," + item.quantity + "," + item.getCategory())
                .collect(Collectors.toList()));
                
        Files.write(Paths.get(fileName), lines);
    }

    // Méthode pour charger la liste d'éléments depuis un fichier CSV
    public void load(List<Item> groceryList, String fileName) throws IOException {
        groceryList.clear();
        
        if (!Files.exists(Paths.get(fileName))) {
            return;
        }
        
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        
        // Skip header line
        boolean isFirstLine = true;
        
        for (String line : lines) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            
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
}
