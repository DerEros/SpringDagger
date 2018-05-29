package example.springdagger.central.routes.handlers;

import example.springdagger.central.model.Ingredient;
import example.springdagger.central.services.PizzaCatalogService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IngredientsHandler {
    private final PizzaCatalogService pizzaCatalogService;

    public IngredientsHandler(PizzaCatalogService pizzaCatalogService) {
        this.pizzaCatalogService = pizzaCatalogService;
    }

    public Flux<Ingredient> getAllIngredients() {
        return pizzaCatalogService.getAllIngredients();
    }

    public Mono<Ingredient> getIngredient(Long id) {
        return pizzaCatalogService.getIngredientById(id);
    }
}
