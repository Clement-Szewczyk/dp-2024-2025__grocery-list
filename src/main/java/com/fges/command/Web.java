package com.fges.command;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fges.GroceryListManager;

import fr.anthonyquere.GroceryShopServer;
import fr.anthonyquere.MyGroceryShop;

/**
 * Commande pour exécuter le serveur web de l'application
 */
public class Web implements Command {
    // Constantes pour les messages (facilitera l'internationalisation future)
    private static final String MSG_SERVER_STARTED = "Serveur web démarré sur http://localhost:{}";
    private static final String MSG_PORT_INVALID = "Port invalide: %s";
    private static final String MSG_SERVER_ERROR = "Erreur lors du démarrage du serveur web";
    private static final int DEFAULT_PORT = 8080;
    
    // Logger au lieu de System.out/err
    private static final Logger logger = LoggerFactory.getLogger(Web.class);

    @Override
    public int execute(GroceryListManager manager) {
        try {
            // Extraction des méthodes pour mieux séparer les responsabilités
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
    
    /**
     * Configure le port du serveur web en fonction des arguments
     * @return le port configuré ou le port par défaut
     * @throws NumberFormatException si le port spécifié n'est pas un nombre valide
     */
    private int configureServerPort() throws NumberFormatException {
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
     * Crée un adaptateur pour le serveur web basé sur le gestionnaire de liste de courses
     * @param manager le gestionnaire de liste de courses
     * @return l'adaptateur configuré
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
                // Utiliser la classe Info pour obtenir les informations système
                Info infoCommand = new Info();
                return infoCommand.getSystemInfo();
            }
        };
    }
    
    /**
     * Démarre le serveur web sur le port spécifié avec l'adaptateur fourni
     * @param port le port sur lequel démarrer le serveur
     * @param groceryShop l'adaptateur pour la boutique
     * @return le code de sortie (0 si réussi, 1 si échec)
     */
    private int startServer(int port, MyGroceryShop groceryShop) {
        GroceryShopServer server = new GroceryShopServer(groceryShop);

        try {
            server.start(port);
            logger.info("Serveur web démarré sur http://localhost:" + port);
            // Maintient le thread actif pour garder le serveur en cours d'exécution
            Thread.currentThread().join();
            return 0;
        } catch (Exception e) {
            logger.error(MSG_SERVER_ERROR, e);
            return 1;
        }
    }
}