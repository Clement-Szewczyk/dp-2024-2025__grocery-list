package com.fges;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.anthonyquere.GroceryShopServer;
import fr.anthonyquere.MyGroceryShop;

public class Main {
    public static void main(String[] args) throws IOException {
        // Pour tester le mode web, décommente la ligne suivante :
        //runWebServer();

        // Pour le mode CLI, laisse la ligne suivante :
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        return CLI.exec(args);
    }

    public static void runWebServer() {
        MyGroceryShop groceryShop = new MyGroceryShop() {
            private final List<WebGroceryItem> items = new ArrayList<>();

            @Override
            public List<WebGroceryItem> getGroceries() {
                return new ArrayList<>(items);
            }

            @Override
            public void addGroceryItem(String name, int quantity, String category) {
                items.add(new WebGroceryItem(name, quantity, category));
            }

            @Override
            public void removeGroceryItem(String name) {
                items.removeIf(item -> item.name().equalsIgnoreCase(name));
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

        GroceryShopServer server = new GroceryShopServer(groceryShop);
        server.start(8080);

        System.out.println("Grocery shop server started at http://localhost:8080");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}