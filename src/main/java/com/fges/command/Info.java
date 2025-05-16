package com.fges.command;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fges.GroceryListManager;

import fr.anthonyquere.MyGroceryShop.Runtime;

public class Info implements Command {
    @Override
    public int execute(GroceryListManager manager) {
        // Utiliser la méthode getSystemInfo et afficher les informations
        Runtime runtime = getSystemInfo();

        System.out.println("Today's date: " + LocalDate.now());
        System.out.println("Operating System: " + runtime.osName());
        System.out.println("Java version: " + runtime.javaVersion());
        
        return 0; // Success status code
    }
    
    /**
     * Récupère les informations système sous forme d'objet Runtime
     * @return Un objet Runtime contenant les informations système
     */
    public Runtime getSystemInfo() {
        LocalDate today = LocalDate.now();
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        
        return new Runtime(today, javaVersion, osName);
    }
}
