package example.springdagger.decentral.util;

import example.springdagger.decentral.model.Ingredient;
import example.springdagger.decentral.model.Pizza;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FakePizzas {
    private final List<Pizza> pizzas;
    private final FakeIngredients fakeIngredients;

    public FakePizzas() {
        fakeIngredients = new FakeIngredients();
        pizzas = generatePizzas();
    }

    private List<Pizza> generatePizzas() {
        return Arrays.asList(
                generatePizzaWithIngredients(1, 1, 3, 5),
                generatePizzaWithIngredients(2, 2, 4, 6),
                generatePizzaWithIngredients(3, 1, 4, 6),
                generatePizzaWithIngredients(4, 2, 3, 5)
        );
    }

    private Pizza generatePizzaWithIngredients(Integer pizzaId, Integer... ids) {
        List<Ingredient> ingredients = Stream.of(ids)
                .map(Long::valueOf)
                .map(fakeIngredients::getIngredientById)
                .collect(Collectors.toList());
        return new Pizza(Long.valueOf(pizzaId), ingredients);
    }

    public Pizza getPizza(Integer index) {
        return pizzas.get(index);
    }
}
