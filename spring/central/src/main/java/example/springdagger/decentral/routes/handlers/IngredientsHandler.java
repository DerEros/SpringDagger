package example.springdagger.decentral.routes.handlers;

import example.springdagger.decentral.model.Ingredient;
import example.springdagger.decentral.services.PizzaCatalogService;
import reactor.core.publisher.Flux;

public class IngredientsHandler {
    private final PizzaCatalogService pizzaCatalogService;

    public IngredientsHandler(PizzaCatalogService pizzaCatalogService) {
        this.pizzaCatalogService = pizzaCatalogService;
    }

    public Flux<Ingredient> getAllIngredients() {
        return Flux.empty();
    }
}
