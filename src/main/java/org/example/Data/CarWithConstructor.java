package org.example.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// Must
public class CarWithConstructor {
    private String brand;

    @JsonCreator
    public CarWithConstructor(@JsonProperty("brand") String brand) {
        this.brand = brand;
    }
}
