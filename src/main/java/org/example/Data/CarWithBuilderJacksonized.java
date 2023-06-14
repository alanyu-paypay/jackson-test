package org.example.Data;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public class CarWithBuilderJacksonized {
    private String brand;
}
