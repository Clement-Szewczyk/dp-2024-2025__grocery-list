package com.fges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class GroceryFile {
    protected final String fileName;
    protected List<String> groceryList;

    public GroceryFile(String fileName) {
        this.fileName = fileName;
        this.groceryList = new ArrayList<>();
        loadFromFile();
    }

    protected void loadFromFile() {
        Path filePath = Paths.get(fileName);
        try {
            if (Files.exists(filePath)) {
                parseFileContents(Files.readAllLines(filePath));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    protected abstract void parseFileContents(List<String> fileContents);

    protected abstract void saveToFile();

    public abstract void add(String itemName, int quantity);

    public void remove(String itemName) {
        remove(itemName, 0);
    }

    public abstract void remove(String itemName, int quantity);

    public abstract List<String> list();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "fileName='" + fileName + '\'' +
                ", groceryList=" + groceryList +
                '}';
    }
}