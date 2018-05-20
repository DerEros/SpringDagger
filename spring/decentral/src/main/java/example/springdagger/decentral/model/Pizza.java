package example.springdagger.decentral.model;

import java.util.List;
import java.util.Optional;

public class Pizza {
    private final Optional<Long> id;
    private final List<Ingredient> ingredients;

    public Pizza(Long id, List<Ingredient> ingredients) {
        this.id = Optional.of(id);
        this.ingredients = ingredients;
    }

    public Pizza(List<Ingredient> ingredients) {
        this.id = Optional.empty();
        this.ingredients = ingredients;
    }

    public Optional<Long> getId() {
        return id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
