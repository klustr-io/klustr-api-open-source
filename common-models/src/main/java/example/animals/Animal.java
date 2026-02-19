
package example.animals;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Animal
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "name"
})
@Generated("jsonschema2pojo")
public class Animal {

    /**
     * Discriminator for animal subtype
     * (Required)
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("Discriminator for animal subtype")
    private Animal.Type type;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    private String name;

    /**
     * Discriminator for animal subtype
     * (Required)
     * 
     */
    @JsonProperty("type")
    public Animal.Type getType() {
        return type;
    }

    /**
     * Discriminator for animal subtype
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(Animal.Type type) {
        this.type = type;
    }

    public Animal withType(Animal.Type type) {
        this.type = type;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Animal withName(String name) {
        this.name = name;
        return this;
    }


    /**
     * Discriminator for animal subtype
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Type {

        CAT("cat"),
        DOG("dog");
        private final String value;
        private final static Map<String, Animal.Type> CONSTANTS = new HashMap<String, Animal.Type>();

        static {
            for (Animal.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Animal.Type fromValue(String value) {
            Animal.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
