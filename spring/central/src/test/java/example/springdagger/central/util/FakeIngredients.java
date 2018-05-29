package example.springdagger.central.util;

import example.springdagger.central.model.Ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class FakeIngredients {
    private final List<Ingredient> dummyIngredients = createDummyIngredients();
    private final Map<Long, Ingredient> dummyIngredientsById = mapById(dummyIngredients);

    private List<Ingredient> createDummyIngredients() {
        return Arrays.asList(new Ingredient(1L, Ingredient.Type.DOUGH, "Thin Dough", 1.0f),
                new Ingredient(2L, Ingredient.Type.DOUGH, "Thick Dough", 1.5f),
                new Ingredient(3L, Ingredient.Type.SAUCE, "Tomato Sauce", 0.5f),
                new Ingredient(4L, Ingredient.Type.SAUCE, "BBQ Sauce", 0.7f),
                new Ingredient(5L, Ingredient.Type.TOPPING, "Cheese", 0.0f),
                new Ingredient(6L, Ingredient.Type.TOPPING, "Salami", 0.5f)
        );
    }

    private Map<Long, Ingredient> mapById(List<Ingredient> ingredients) {
        return ingredients.stream().collect(toMap(Ingredient::getId, Function.identity()));
    }

    public List<Ingredient> getAllIngredients() {
        return dummyIngredients;
    }

    public Ingredient getIngredientById(Long id) {
        return dummyIngredientsById.get(id);
    }
}
