package com.fges;

import com.fges.dao.GroceryListDAOFactory;

public class GroceryListApplicationFactory {
    public static CommandHandler createApplication(String fileName, String format) {
        // Create DAO
        var dao = GroceryListDAOFactory.createDAO(fileName, format);

        // Create manager
        var manager = new GroceryListManager(dao);
        manager.initialize();

        // Create command handler
        return new CommandHandler(manager);
    }
}