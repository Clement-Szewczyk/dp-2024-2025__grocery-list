package com.fges.command;

import com.fges.GroceryListManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Info implements Command {
    @Override
    public int execute(GroceryListManager manager) {
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("Today's date : " + todayDate);
        System.out.println("Operating System : " + osName);
        System.out.println("Java version : " + javaVersion);
        return 0;
    }
}