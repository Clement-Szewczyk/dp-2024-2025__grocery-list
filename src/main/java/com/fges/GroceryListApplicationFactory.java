package com.fges;

import com.fges.dao.GroceryListDAOFactory;
/**
 * Factory class for creating instances of the grocery list application components.
 * This class follows the Factory design pattern to centralize the creation of the application objects.
 * It handles the initialization of the DAO, manager, and command handler components,
 * providing a fully configured application ready for use.
 *
 * @author Szewczyk Clément, Stievenard Emma, Laurency Yuna
 */
public class GroceryListApplicationFactory {

    /**
     * Creates and initializes a CommandHandler configured with appropriate dependencies.
     * This method provides a simple interface for creating a fully set up application instance.
     * It creates the necessary DAO based on the specified format, initializes the manager,
     * and connects them to a new CommandHandler.
     *
     * @param fileName The name of the file to store grocery list data
     * @param format The format of the file (e.g., "json" or "csv")
     * @return A fully initialized CommandHandler ready to process commands
     */
    public static CommandHandler createApplication(String fileName, String format) {
        // Create the appropriate Data Access Object using the factory
        var dao = GroceryListDAOFactory.createDAO(fileName, format);

        // Create and initialize the GroceryListManager with the DAO
        var manager = new GroceryListManager(dao);
        manager.initialize();

        /// Create and return the CommandHandler connected to the manager
        return new CommandHandler(manager);
    }
}