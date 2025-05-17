package com.fges.command;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fges.GroceryListManager;

import fr.anthonyquere.GroceryShopServer;
import fr.anthonyquere.MyGroceryShop;

public class Web implements Command {
    // Constants for messages (will facilitate future internationalization)
    private static final String MSG_SERVER_STARTED = "Web server started on http://localhost:{}";
    private static final String MSG_PORT_INVALID = "Invalid port: %s";
    private static final String MSG_SERVER_ERROR = "Error starting the web server";
    private static final int DEFAULT_PORT = 8080;
    
    // Logger au lieu de System.out/err
    private static final Logger logger = LoggerFactory.getLogger(Web.class);

    @Override
    public int execute(GroceryListManager manager) {
        try {
            // Extraction of methods to better separate responsibilities
            int port = configureServerPort();
            MyGroceryShop groceryShop = createGroceryShopAdapter(manager);
            return startServer(port, groceryShop);
        } catch (NumberFormatException e) {
            logger.error(MSG_PORT_INVALID, e.getMessage());
            return 1;
        } catch (Exception e) {
            logger.error(MSG_SERVER_ERROR, e);
            return 1;
        }
    }

    public int configureServerPort() throws NumberFormatException {
        CommandOption options = CommandOption.getInstance();
        String[] args = options.getCommandArgs();

        if (args.length <= 1) {
            return DEFAULT_PORT;
        }
        
        try {
            return Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(String.format(MSG_PORT_INVALID, args[1]));
        }
    }
    
    /**
     * Creates an adapter for the web server based on the grocery list manager
     */
    private MyGroceryShop createGroceryShopAdapter(final GroceryListManager manager) {
        return new MyGroceryShop() {
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
                // Use the Info class to get system information
                Info infoCommand = new Info();
                return infoCommand.getSystemInfo();
            }
        };
    }

    private int startServer(int port, MyGroceryShop groceryShop) {
        GroceryShopServer server = new GroceryShopServer(groceryShop);

        try {
            server.start(port);
            logger.info("Web server started on http://localhost:" + port);
            // Keeps the current thread active to keep the server running
            Thread.currentThread().join();
            return 0;
        } catch (Exception e) {
            logger.error(MSG_SERVER_ERROR, e);
            return 1;
        }
    }
}