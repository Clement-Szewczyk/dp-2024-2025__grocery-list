package com.fges.command;

import com.fges.GroceryItem;
import com.fges.GroceryListManager;
import fr.anthonyquere.GroceryShopServer;
import fr.anthonyquere.MyGroceryShop;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Web implements Command {
    @Override
    public int execute(GroceryListManager manager) {
        CommandOption options = CommandOption.getInstance();
        String[] args = options.getCommandArgs();

        // Parse le port (argument après "web")
        int port = 8080; // Port par défaut
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Port invalide: " + args[1]);
                return 1;
            }
        }

        // Crée l'adapter pour le serveur web
        MyGroceryShop groceryShop = new MyGroceryShop() {
            @Override
            public List<WebGroceryItem> getGroceries() {
                return manager.getGroceryList().stream()
                        .map(item -> new WebGroceryItem(
                                item.getName(),
                                item.getQuantity(),
                                item.getCategory()
                        ))
                        .collect(Collectors.toList());
            }

            @Override
            public void addGroceryItem(String name, int quantity, String category) {
                manager.add(name, quantity, category);
                manager.saveToFile();
            }

            @Override
            public void removeGroceryItem(String name) {
                manager.remove(name);
                manager.saveToFile();
            }

            @Override
            public Runtime getRuntime() {
                return new Runtime(
                        LocalDate.now(),
                        System.getProperty("java.version"),
                        System.getProperty("os.name")
                );
            }
        };

        // Démarre le serveur
        GroceryShopServer server = new GroceryShopServer(groceryShop);

        try {
            server.start(port);
            System.out.println("Serveur web démarré sur http://localhost:" + port);

            // Maintient le serveur actif
            Thread.currentThread().join();
            return 0;
        } catch (Exception e) {
            System.err.println("Erreur lors du démarrage du serveur web: " + e.getMessage());
            return 1;
        }
    }
}