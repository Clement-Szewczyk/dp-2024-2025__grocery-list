package com.fges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSON {

    private final String fileName; // Nom du fichier
    private List<String> groceryList; // La liste de course
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(); // Manipuler le fichier JSON

    public JSON(String fileName) {
        this.fileName = fileName;
        this.groceryList = new ArrayList<>();
        loadFromFile();
    }

    private void loadFromFile() { // Charger et lire un fichier
        Path filePath = Paths.get(fileName);
        try {
            if (Files.exists(filePath)) {
                String fileContent;
                fileContent = Files.readString(filePath);
                var parsedList = OBJECT_MAPPER.readValue(fileContent, new TypeReference<List<String>>() {
                });
                groceryList = new ArrayList<>(parsedList);
            }
            else{
                groceryList = new ArrayList<>();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveToFile() { // Sauvegarder un fichier
        try {
            var outputFile = new File(fileName);
            OBJECT_MAPPER.writeValue(outputFile, groceryList);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void add(String itemName, int quantity) { // Ajouter un élément
        boolean itemFound = false;
        for (int i = 0; i < groceryList.size(); i++) {
            String currentItem = groceryList.get(i);
            if (currentItem.startsWith(itemName)) {
                int currentQuantity = Integer.parseInt(currentItem.split(",")[1]); // Récupérer la quantité
                groceryList.set(i, itemName + "," + (currentQuantity + quantity)); // Mise à jour de la quantité
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            groceryList.add(itemName + "," + quantity);
        }
        saveToFile();
    }

    public void remove(String itemName) { // Supprimer tous les éléments
        remove(itemName,0);
    }

    public void remove(String itemName, int quantity) { // Supprimer un élément avec les quantités (surcharge)
        if (quantity <= 0) {
            groceryList = groceryList.stream()
                    .filter(item -> !item.startsWith(itemName + ","))
                    .toList();
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
        saveToFile(); // Sauvegarde les modifications
    }

    public void list() { // Afficher tous les éléments
        List<String> items = groceryList;
        for (String item : items) {
            System.out.println(item);
        }
    }

    @Override
    public String toString() {
        return "JSON{" + "fileName='" + fileName + '\'' + ", groceryList=" + groceryList + '}';
    }

    public static void main(String[] args) {
        try {
            // Nom du fichier
            String fileName = "grocery.json";

            // Instance JSON
            JSON jsonManager = new JSON(fileName);

            // Ajout d'éléments
            System.out.println("\nAjout d'elements a la liste de courses...");
            jsonManager.add("Pomme", 5);
            jsonManager.add("Banane", 3);
            jsonManager.add("Orange", 2);

            // Affichage des éléments
            System.out.println("\nListe de courses actuelle :");
            jsonManager.list();

            // Suppression d'un élément
            System.out.println("\nSuppression de Banane de la liste...");
            jsonManager.remove("Banane",1);

            // Affichage des éléments après suppression
            System.out.println("\nListe de courses mise à jour :");
            jsonManager.list();

            // Vérification du contenu du fichier
            System.out.println("\nVerification du contenu du fichier :");
            Path path = Paths.get(fileName);
            if (Files.exists(path)) {
                List<String> fileContent = Files.readAllLines(path);
                for (String line : fileContent) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
