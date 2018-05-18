package example.springdagger.decentral.controller;

import example.springdagger.decentral.data.IngredientsDAO;
import example.springdagger.decentral.model.Ingredient;
import example.springdagger.decentral.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

@RestController
public class PizzaCatalogController {

    private final IngredientsDAO ingredientsDAO;

    @Inject
    public PizzaCatalogController(IngredientsDAO ingredientsDAO) {
        this.ingredientsDAO = ingredientsDAO;
    }

    @GetMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Flux<Ingredient> getIngredients() {
        return ingredientsDAO.getAllIngredients();
    }

    @GetMapping(value = "/ingredients/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<Ingredient> getIngredientById(@PathVariable("id") Long id) {
        return ingredientsDAO.getIngredientById(id);
    }

    @PostMapping(value = "/order",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> placeOrder(@RequestBody Mono<Order> order) {
        return Mono.empty();
    }
}
