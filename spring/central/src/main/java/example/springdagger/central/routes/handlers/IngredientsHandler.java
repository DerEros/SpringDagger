package example.springdagger.central.routes.handlers;

import example.springdagger.central.data.transfer.dto.NewPizzaOrder;
import example.springdagger.central.model.Ingredient;
import example.springdagger.central.model.Pizza;
import example.springdagger.central.services.PizzaCatalogService;
import example.springdagger.central.services.PizzaOrderService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IngredientsHandler {
    private final PizzaCatalogService pizzaCatalogService;
    private final PizzaOrderService pizzaOrderService;

    public IngredientsHandler(PizzaCatalogService pizzaCatalogService, PizzaOrderService pizzaOrderService) {
        this.pizzaCatalogService = pizzaCatalogService;
        this.pizzaOrderService = pizzaOrderService;
    }

    public Flux<Ingredient> getAllIngredients() {
        return pizzaCatalogService.getAllIngredients();
    }

    public Mono<Ingredient> getIngredient(Long id) {
        return pizzaCatalogService.getIngredientById(id);
    }

    public Flux<Pizza> getPizzas(Integer amount) {
        return pizzaCatalogService.getPredefPizzas(amount);
    }

    public Mono<Void> orderPizza(Mono<NewPizzaOrder> newPizzaOrder) {
        return pizzaOrderService.orderPizza(newPizzaOrder);
    }
}
