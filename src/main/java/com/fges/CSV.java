package com.fges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CSV {

    // Méthode pour sauvegarder la liste d'éléments au format CSV dans un fichier
    public void save(List<Item> groceryList, String fileName) throws IOException {
        List<String> lines = groceryList.stream()
                .map(item -> item.getName() + "," + item.quantity)
                .collect(Collectors.toList());
        Files.write(Paths.get(fileName), lines);
    }

    // Méthode pour charger la liste d'éléments depuis un fichier CSV
    public void load(List<Item> groceryList, String fileName) throws IOException {
        List<String> fileContents = Files.readAllLines(Paths.get(fileName));
        groceryList.clear();
        for (String line : fileContents) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                try {
                    String itemName = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    groceryList.add(new Item(itemName, quantity));
                } catch (NumberFormatException e) {
                    System.err.println("Format invalide dans le fichier : " + line);
                }
            } else {
                System.err.println("Ligne ignorée (mauvais format) : " + line);
            }
        }
    }
}
