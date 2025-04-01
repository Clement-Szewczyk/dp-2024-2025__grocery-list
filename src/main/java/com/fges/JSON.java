package com.fges;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JSON {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public void save(List<Item> groceryList, String fileName) throws IOException {
        List<String> formattedList = groceryList.stream()
                .map(item -> item.getName() + ": " + item.quantity)
                .collect(Collectors.toList());

        OBJECT_MAPPER.writeValue(new File(fileName), formattedList);
    }

    public void load(List<Item> groceryList, String fileName) throws IOException {
        groceryList.clear();

        List<String> loadedList = OBJECT_MAPPER.readValue(
                new File(fileName),
                OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, String.class)
        );
        for (String entry : loadedList) {
            String[] parts = entry.split(":");
            if (parts.length == 2) {
                try {
                    String name = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    groceryList.add(new Item(name, quantity));
                } catch (NumberFormatException e) {
                    System.err.println("Format invalide dans le fichier JSON : " + entry);
                }
            }
        }
    }
}
