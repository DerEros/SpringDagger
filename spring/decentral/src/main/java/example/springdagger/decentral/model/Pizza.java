package example.springdagger.decentral.model;

import java.util.List;
import java.util.Optional;

public class Pizza {
    private Optional<Long> id;
    private List<Ingredient> ingredients;

    public Pizza(Long id, List<Ingredient> ingredients) {
        this.id = Optional.of(id);
        this.ingredients = ingredients;
    }

    public Pizza(List<Ingredient> ingredients) {
        this.id = Optional.empty();
        this.ingredients = ingredients;
    }

    public Pizza() {
    }

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
