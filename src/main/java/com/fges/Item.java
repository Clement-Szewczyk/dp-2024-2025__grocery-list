package com.fges;

public class Item {
    // Classe qui représente un élément de la liste de courses avec un nom et une quantité. MONTRE TOI
    public String name;
    public int quantity;
    public Item(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }
}
