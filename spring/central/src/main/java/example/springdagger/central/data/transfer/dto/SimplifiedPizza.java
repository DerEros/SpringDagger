package example.springdagger.central.data.transfer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SimplifiedPizza {
    private final List<Long> ingredientIDs;

    public SimplifiedPizza(@JsonProperty(value = "ingredients", required = true) List<Long> ingredientIDs) {
        this.ingredientIDs = ingredientIDs;
    }

    public List<Long> getIngredientIDs() {
        return ingredientIDs;
    }
}
