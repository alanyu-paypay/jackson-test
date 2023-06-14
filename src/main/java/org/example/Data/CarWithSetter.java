package org.example.Data;

// Setter Makes a Non-Public Field Deserializable Only
public class CarWithSetter {
    private String brand;

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
