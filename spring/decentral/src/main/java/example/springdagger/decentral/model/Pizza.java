package example.springdagger.decentral.model;

import java.util.List;

public class Pizza {
    private Long id;
    private List<Ingredient> ingredients;

    public Pizza(Long id, List<Ingredient> ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
