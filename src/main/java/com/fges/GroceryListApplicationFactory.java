package com.fges;

import com.fges.dao.GroceryListDAOFactory;
/**
 * Factory class for creating instances of the grocery list application components.
 * It handles the initialization of the DAO, manager, and command handler components,
 providing a fully configured application ready for use.
 */
public class GroceryListApplicationFactory {

    public static CommandHandler createApplication(String fileName, String format) {
        // Create the appropriate DAO using the factory
        var dao = GroceryListDAOFactory.createDAO(fileName, format);

        // Create and initialize the GroceryListManager with the DAO
        var manager = new GroceryListManager(dao);
        manager.initialize();

        /// Create and return the CommandHandler connected to the manager
        return new CommandHandler(manager);
    }
}