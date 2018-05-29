package example.springdagger.central.controller;

import example.springdagger.central.data.transfer.dto.NewPizzaOrder;
import example.springdagger.central.model.Ingredient;
import example.springdagger.central.model.Pizza;
import example.springdagger.central.services.PizzaCatalogService;
import example.springdagger.central.services.PizzaOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class PizzaCatalogController {

    private final PizzaCatalogService pizzaCatalogService;
    private final PizzaOrderService pizzaOrderService;

    @Inject
    public PizzaCatalogController(PizzaCatalogService pizzaCatalogService, PizzaOrderService pizzaOrderService) {
        this.pizzaCatalogService = pizzaCatalogService;
        this.pizzaOrderService = pizzaOrderService;
    }

    @GetMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<Ingredient> getIngredients() {
        return pizzaCatalogService.getAllIngredients();
    }

    @GetMapping(value = "/ingredients/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResponseEntity<Ingredient>> getIngredientById(@PathVariable("id") Long id) {
        Mono<Ingredient> ingredient = pizzaCatalogService.getIngredientById(id);

        return ingredient.map(i -> ok().body(i))
                .defaultIfEmpty(notFound().build());
    }

    @GetMapping(value = "/pizza", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<Pizza> getPredefPizza(@RequestParam(value = "amount", defaultValue = "10") Integer amount) {
        return pizzaCatalogService.getPredefPizzas(amount);
    }

    @PostMapping(value = "/order",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> placeOrder(@RequestBody Mono<NewPizzaOrder> order) {
        return pizzaOrderService.orderPizza(order);
    }
}
