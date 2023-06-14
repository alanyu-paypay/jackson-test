package org.example.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Builder & Getter & NoArgsConstructor are bundled, if we want to deserialize the object
// Because jackson deserialization default uses getters and NoArgsConstructor
// If we want to use the Builder to deserialize then we need @Jacksonized
//
// AllArgsConstructor is for builder
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarWithBuilderNoArgsConstructorGetter {
    private String brand;
}
