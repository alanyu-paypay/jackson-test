import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import java.lang.reflect.Field;
import org.example.Data.CarWithBuilderJacksonized;
import org.example.Data.CarWithBuilderNoArgsConstructorGetter;
import org.example.Data.CarWithBuilderOnly;
import org.example.Data.CarWithConstructor;
import org.example.Data.CarWithGetter;
import org.example.Data.CarWithPrivateField;
import org.example.Data.CarWithPublicField;
import org.example.Data.CarWithSetter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeserializeTest {

    @Test
    void Default_Visibility_Deserialize_Should_Throw_Exception_For_Private_Field() {
        ObjectMapper mapper = new ObjectMapper();
        assertThrows(UnrecognizedPropertyException.class, () -> {
            mapper.readValue("{\"brand\":\"bmw\"}", CarWithPrivateField.class);
        });
    }

    @Test
    void Field_Visibility_Public_Deserialize_Should_Pass_For_Private_Field() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Here we set object mapper to detect private field(not recommended)
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        var actual = mapper.readValue("{\"brand\":\"toyota\"}", CarWithPrivateField.class);;

        // Get the private field
        Field privateField = CarWithPrivateField.class.getDeclaredField("brand");

        // Make the private field accessible
        privateField.setAccessible(true);

        // Read the value of the private field
        String brand = (String) privateField.get(actual);
        assertEquals("toyota", brand);
    }

    @Test
    void Deserialize_Should_Pass_For_Public_Field() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        var actual = mapper.readValue("{\"brand\":\"toyota\"}", CarWithPublicField.class);;

        assertEquals("toyota", actual.brand);
    }

    @Test
    void Deserialize_And_Serialize_Should_Pass_For_Private_Field_With_Getter() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize
        var actual = mapper.readValue("{\"brand\":\"toyota\"}", CarWithGetter.class);;
        assertEquals("toyota", actual.getBrand());

        // Serialize
        var carString = mapper.writeValueAsString(actual);
        assertEquals("{\"brand\":\"toyota\"}", carString);
    }

    @Test
    void Deserialize_Should_Pass_For_Private_Field_With_Setter() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize
        var actual = mapper.readValue("{\"brand\":\"toyota\"}", CarWithSetter.class);;

        // Get the private field
        Field privateField = CarWithSetter.class.getDeclaredField("brand");

        // Make the private field accessible
        privateField.setAccessible(true);

        // Read the value of the private field
        String brand = (String) privateField.get(actual);
        assertEquals("toyota", brand);
    }

    @Test
    void Serialize_Should_Fail_For_Private_Field_With_Setter() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize
        var actual = mapper.readValue("{\"brand\":\"toyota\"}", CarWithSetter.class);;


        // Serialize
        assertThrows(InvalidDefinitionException.class, () -> {
            var carString = mapper.writeValueAsString(actual);
        });
    }

    @Test
    void Deserialize_Should_Pass_For_Private_Field_With_Constructor() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize
        var actual = mapper.readValue("{\"brand\":\"toyota\"}", CarWithConstructor.class);

        // Get the private field
        Field privateField = CarWithConstructor.class.getDeclaredField("brand");

        // Make the private field accessible
        privateField.setAccessible(true);

        // Read the value of the private field
        String brand = (String) privateField.get(actual);
        assertEquals("toyota", brand);
    }

    @Test
    void Deserialize_Should_Fail_For_Builder_Only() {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize should fail
        assertThrows(MismatchedInputException.class, () -> {
            var actual = mapper.readValue("{\"brand\":\"toyota\"}", CarWithBuilderOnly.class);;
        });
    }

    @Test
    void Deserialize_Should_Pass_For_Builder_With_NoArgsConstructor_And_Getter() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize
        var actual = mapper.readValue("{\"brand\":\"toyota\"}", CarWithBuilderNoArgsConstructorGetter.class);

        // Read the value
        String brand = actual.getBrand();
        assertEquals("toyota", brand);
    }

    @Test
    void Deserialize_Should_Pass_For_Builder_With_Jacksonized() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Deserialize
        var actual = mapper.readValue("{\"brand\":\"toyota\"}", CarWithBuilderJacksonized.class);

        // Get the private field
        Field privateField = CarWithBuilderJacksonized.class.getDeclaredField("brand");

        // Make the private field accessible
        privateField.setAccessible(true);

        // Read the value of the private field
        String brand = (String) privateField.get(actual);
        assertEquals("toyota", brand);
    }
}
