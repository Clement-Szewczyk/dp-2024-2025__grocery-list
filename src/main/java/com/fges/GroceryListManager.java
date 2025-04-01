package com.fges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroceryListManager {

    // Classe pour gérer la liste de course
    protected String fileName;
    protected List<Item> groceryList;
    protected String fileType;  // Type de fichier (CSV ou JSON)
    private final JSON jsonManager = new JSON();
    private final CSV csvManager = new CSV();

    // Constructeur
    public GroceryListManager(String fileName) {
        this.fileName = fileName;
        this.groceryList = new ArrayList<>();
        loadFromFile();
    }

    // Charger le fichier
    protected void loadFromFile() {
        try {
            if (fileName.endsWith(".csv")) {
                new CSV().load(groceryList, fileName);  // Charge les données CSV
            } else if (fileName.endsWith(".json")) {
                new JSON().load(groceryList, fileName);  // Charge les données JSON
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Sauvegarde du fichier
    protected void saveToFile() {
        try {
            if (fileName.endsWith(".csv")) {
                new CSV().save(groceryList, fileName);  // Sauvegarde CSV
            } else if (fileName.endsWith(".json")) {
                new JSON().save(groceryList, fileName);  // Sauvegarde JSON
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Méthode pour modifier la liste grâce à la commande
    public int handleCommand(String command, String[] args) {
        try {
            if (command.equals("list")) {
                list();
            } else {
                if (args.length < 2) {
                    System.err.println("Missing arguments for " + command + " command");
                    return 1;
                }
                String itemName = args[1];
                switch (command) {
                    case "add" -> {
                        if (args.length >= 3) {
                            int quantity = Integer.parseInt(args[2]);
                            add(itemName, quantity);
                        } else {
                            add(itemName); // Version sans quantité
                        }
                    }
                    case "remove" -> {
                        if (args.length >= 3) {
                            int quantity = Integer.parseInt(args[2]);
                            remove(itemName, quantity);
                        } else {
                            remove(itemName); // Version sans quantité
                        }
                    }
                    default -> {
                        System.err.println("Unknown command: " + command);
                        return 1;
                    }
                }
            }
            saveToFile();
            return 0;

        } catch (NumberFormatException e) {
            System.err.println("Invalid quantity format");
            return 1;
        }
    }

    // Méthode pour ajouter un élément à la liste de course
    public void add(String itemName) {
        add(itemName, 1);
    }

    // Méthode pour ajouter des éléments à la liste de course
    public void add(String itemName, int quantity) {
        boolean itemFound = false;
        for (Item item : groceryList) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                item.quantity += quantity;
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            groceryList.add(new Item(itemName, quantity));
        }
    }

    // Méthode pour supprimer un élément à la liste de course
    public void remove(String itemName, int quantity) {
        if (quantity <= 0) {
            groceryList.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        } else {
            groceryList.forEach(item -> {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    item.quantity -= quantity;
                }
            });
            groceryList.removeIf(item -> item.quantity <= 0);
        }
    }

    // Méthode pour supprimer des éléments de la liste de course
    public void remove(String itemName) {
        remove(itemName, 0);
    }

    // Méthode pour afficher la liste de course
    public void list() {
        if (groceryList.isEmpty()) {
            System.out.println("La liste est vide.");
        } else {
            for (Item item : groceryList) {
                System.out.println(item.getName() + " : " + item.quantity);
            }
        }
    }
}
