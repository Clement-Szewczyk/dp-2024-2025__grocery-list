package com.fges;

public class Item {
    // Classe qui représente un élément de la liste de courses avec un nom et une quantité. MONTRE TOI
    public String name;
    public int quantity;
    private String category;


    // Constructor with category
    public Item(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { // <- setter requis
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) { // <- setter requis
        this.quantity = quantity;
    }
}
