package org.example.Data;

// Getter Makes a Non-Public Field Serializable and Deserializable
public class CarWithGetter {
    
    private String brand;

    public String getBrand() {
        return this.brand;
    }
}
