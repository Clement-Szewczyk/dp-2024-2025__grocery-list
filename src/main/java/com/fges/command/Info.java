package com.fges.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fges.GroceryListManager;

public class Info implements Command {
    @Override
    public int execute(GroceryListManager manager) {
        // Display current date
        LocalDate today = LocalDate.now();
        System.out.println("Today's date: " + today.format(DateTimeFormatter.ISO_LOCAL_DATE));
        
        // Display operating system information
        System.out.println("Operating System: " + System.getProperty("os.name"));
        
        // Display Java version
        System.out.println("Java version: " + System.getProperty("java.version"));
        
        return 0; // Success status code
    }
}
