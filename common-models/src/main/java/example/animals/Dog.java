
package example.animals;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Dog
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "goodBoy"
})
@Generated("jsonschema2pojo")
public class Dog
    extends Animal
{

    @JsonProperty("goodBoy")
    private Boolean goodBoy;

    @JsonProperty("goodBoy")
    public Boolean getGoodBoy() {
        return goodBoy;
    }

    @JsonProperty("goodBoy")
    public void setGoodBoy(Boolean goodBoy) {
        this.goodBoy = goodBoy;
    }

    public Dog withGoodBoy(Boolean goodBoy) {
        this.goodBoy = goodBoy;
        return this;
    }

    @Override
    public Dog withType(Animal.Type type) {
        super.withType(type);
        return this;
    }

    @Override
    public Dog withName(String name) {
        super.withName(name);
        return this;
    }

}
