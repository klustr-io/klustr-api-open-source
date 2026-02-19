
package example.animals;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Cat
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "lives"
})
@Generated("jsonschema2pojo")
public class Cat
    extends Animal
{

    @JsonProperty("lives")
    private Integer lives = 9;

    @JsonProperty("lives")
    public Integer getLives() {
        return lives;
    }

    @JsonProperty("lives")
    public void setLives(Integer lives) {
        this.lives = lives;
    }

    public Cat withLives(Integer lives) {
        this.lives = lives;
        return this;
    }

    @Override
    public Cat withType(Animal.Type type) {
        super.withType(type);
        return this;
    }

    @Override
    public Cat withName(String name) {
        super.withName(name);
        return this;
    }

}
